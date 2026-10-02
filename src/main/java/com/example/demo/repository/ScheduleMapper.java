package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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


	// IDを指定して予定を1件取得する
	@Select("""
		SELECT
			id,
			schedule_date AS scheduleDate,
			title,
			detail
		FROM schedules
		WHERE id = #{id}
		""")
	Schedule selectById(Integer id);


	// IDを指定して予定を1件削除する
	@Delete("""
		DELETE FROM schedules
		WHERE id = #{id}
		""")
	void deleteById(Integer id);
	
	//IDを指定して予定を更新
	@Update("""
			UPDATE schedules
			SET
			schedule_date =#{scheduleDate},
			title =#{title},
			detail =#{datail}
			WHERE id =#{id}
			
			""")
	
	void update(Schedule schedule);
	
}