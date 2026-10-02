package com.example.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Schedule;

@Mapper
public interface ScheduleMapper {

	// 予定を1件登録する
	@Insert("""
		INSERT INTO schedules(schedule_date, title, detail)
		VALUES(#{scheduleDate}, #{title}, #{detail})
		""")
	void insert(Schedule schedule);
}