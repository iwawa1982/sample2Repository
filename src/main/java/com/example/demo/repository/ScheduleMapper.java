package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Schedule;

@Mapper
public interface ScheduleMapper {

	// 予定を1件登録する
	@Insert("""
		INSERT INTO schedules(schedule_date, title, detail)
		VALUES(#{scheduleDate}, #{title}, #{detail})
		""")
	void insert(Schedule schedule);


	// 予定をすべて取得する
	@Select("""
		SELECT
			id,
			schedule_date AS scheduleDate,
			title,
			detail
		FROM schedules
		ORDER BY schedule_date
		""")
	List<Schedule> selectAll();
}