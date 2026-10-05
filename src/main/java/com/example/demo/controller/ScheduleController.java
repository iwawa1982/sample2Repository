package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Schedule;
import com.example.demo.repository.ScheduleMapper;
import com.example.demo.service.HolidayService;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

	private final ScheduleMapper scheduleMapper;

	private final HolidayService holidayService;
	
	public ScheduleController(
			ScheduleMapper scheduleMapper,
			HolidayService holidayService) {

		this.scheduleMapper = scheduleMapper;
		this.holidayService = holidayService;
	}

	@GetMapping
	public String list(
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer month,
			Model model) {

		 // 内閣府から祝日データを取得
	//    String holidayCsv = holidayService.getHoliday();

	    // 取得した祝日データをコンソールに表示
	 // 取得した祝日データをコンソールに表示
	//    System.out.println("===== 祝日CSVここから =====");
	//    System.out.println(holidayCsv);
	//    System.out.println("===== 祝日CSVここまで =====");
		
		// 今日の日付を取得
		LocalDate today = LocalDate.now();

		// yearとmonthが指定されていなければ現在の年月を使用
		if (year == null || month == null) {
			year = today.getYear();
			month = today.getMonthValue();
		}

		
		//祝日データを取得
		
		Map<LocalDate,String> holidays = holidayService.getHolidayMap();
		
		// 表示する月の1日
		LocalDate currentMonth = LocalDate.of(year, month, 1);

		//1日の曜日を数値で取得
		//日曜日=0,月曜日=1
		int firstDayOfWeek = currentMonth.getDayOfWeek().getValue()%7;
		model.addAttribute("firstDayOfWeek", firstDayOfWeek);


		//その月の日数を取得
		int lengthOfMonth = currentMonth.lengthOfMonth();
		model.addAttribute("lengthOfMonth", lengthOfMonth);
		// 前月
		LocalDate previousMonth = currentMonth.minusMonths(1);

		// 翌月
		LocalDate nextMonth = currentMonth.plusMonths(1);

		// HTMLへ渡す
		model.addAttribute("year", year);
		model.addAttribute("month", month);

		
		//祝日データをHTMLへわたす
		model.addAttribute("holidays",holidays);
		model.addAttribute("previousYear", previousMonth.getYear());
		model.addAttribute("previousMonth", previousMonth.getMonthValue());

		model.addAttribute("nextYear", nextMonth.getYear());
		model.addAttribute("nextMonth", nextMonth.getMonthValue());

		// =============================
		// 予定データ
		// =============================

		List<Schedule> schedules = scheduleMapper.selectAll();

		//表示中の月予定を入れるリスト
		List<Schedule> monthlySchedules =new ArrayList<>();


		//予定がある日を格納するリスト
		List<Integer> scheduledDays = new ArrayList<>();

		//予定を1件ずつ取り出す
		for (Schedule schedule : schedules) {

			//現在表示している年月日と同じ予定か確認
			if(schedule.getScheduleDate().getYear()==year
					&& schedule.getScheduleDate().getMonthValue()==month) {
				//表示中の月の予定として追加
				monthlySchedules.add(schedule);

				//「日」の部分をリストに追加
				scheduledDays.add(
						schedule.getScheduleDate().getDayOfMonth()
						);


			}

		}


		// HTMLへ渡す
		model.addAttribute("scheduledDays", scheduledDays);


		//予定一覧もHTMLへわたす
		model.addAttribute("schedules",monthlySchedules);

		return "schedule/list";
	}

	// 新規登録画面を表示

	@GetMapping("/form")
	public String showForm(Model model) {
		Schedule schedule =new Schedule();

		//HTMLへ渡す
		model.addAttribute("schedule",schedule);

		return "schedule/form";
	}
	
	//編集画面を表示
	
	@GetMapping("/edit")
	public String showEdit(
			@RequestParam Integer id,
			Model model
			) {
		
		//IDを使ってDBから予定を１件取得
		Schedule schedule =scheduleMapper.selectById(id);
		
		//取得した予定をHTMLへ渡す
		model.addAttribute("schedule",schedule);
		
		
		
		//編集画面を表示
		return "schedule/edit";
	}
	
	//予定登録処理

	@PostMapping("/register")
	public String register(Schedule schedule) {


		//入力された予定をDBへ登録

		scheduleMapper.insert(schedule);

		//入力された内容をコンソールに表示
		System.out.println("予定日:"+ schedule.getScheduleDate());
		System.out.println("予定:"+ schedule.getTitle());
		System.out.println("詳細："+schedule.getDetail());
		//登録後は予定一覧へ移動


				return "redirect:/schedules";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam Integer id) {

		//指定されたIDの予定をDBから削除
		scheduleMapper.deleteById(id);



		//登録後は予定一覧へ移動


		return "redirect:/schedules";
	}
	//予定更新処理
	@PostMapping("/update")
	public String update(Schedule schedule) {
		
		//入力された内容でDBを更新
		scheduleMapper.update(schedule);
		
		//更新後は予定一覧へ
		return "redirect:/schedules";
	}

}