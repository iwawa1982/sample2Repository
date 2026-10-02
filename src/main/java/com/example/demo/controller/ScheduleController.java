package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Schedule;
@Controller
@RequestMapping("/schedules")
public class ScheduleController {

	@GetMapping
	public String list(
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer month,
			Model model) {

		// 今日の日付を取得
		LocalDate today = LocalDate.now();

		// yearとmonthが指定されていなければ現在の年月を使用
		if (year == null || month == null) {
			year = today.getYear();
			month = today.getMonthValue();
		}

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

		model.addAttribute("previousYear", previousMonth.getYear());
		model.addAttribute("previousMonth", previousMonth.getMonthValue());

		model.addAttribute("nextYear", nextMonth.getYear());
		model.addAttribute("nextMonth", nextMonth.getMonthValue());

		// =============================
		// ダミーの予定データ
		// =============================

		// 予定一覧を作成
		List<Schedule> schedules = new ArrayList<>();

		// 1件目
		Schedule schedule1 = new Schedule();
		schedule1.setId(1);
		schedule1.setScheduleDate(LocalDate.of(2026, 9, 15));
		schedule1.setTitle("病院");
		schedule1.setDetail("10:00から");

		schedules.add(schedule1);

		// 2件目
		Schedule schedule2 = new Schedule();
		schedule2.setId(2);
		schedule2.setScheduleDate(LocalDate.of(2026, 9, 24));
		schedule2.setTitle("面接");
		schedule2.setDetail("13:00から");

		schedules.add(schedule2);

		//予定がある日を格納するリスト
		List<Integer> scheduledDays = new ArrayList<>();

		//予定を1件ずつ取り出す
		for (Schedule schedule : schedules) {

			//現在表示している年月日と同じ予定か確認
			if(schedule.getScheduleDate().getYear()==year
					&& schedule.getScheduleDate().getMonthValue()==month) {

				//「日」の部分をリストに追加
				scheduledDays.add(
						schedule.getScheduleDate().getDayOfMonth()
						);


			}

		}


		// HTMLへ渡す
		model.addAttribute("scheduledDays", scheduledDays);


		//予定一覧もHTMLへわたす
		model.addAttribute("schedules",schedules);

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
		//予定登録処理
		
		@PostMapping("/register")
		public String register(Schedule schedule) {
			
			//入力された内容をコンソールに表示
			System.out.println("予定日:"+ schedule.getScheduleDate());
			System.out.println("予定:"+ schedule.getTitle());
			System.out.println("詳細："+schedule.getDetail());
			
			
			
			//登録後は予定一覧へ移動
		
		
		return "redirect:/schedules";
	}
}