package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Schedule {

	// 予定ID
	private Integer id;

	// 予定日
	private LocalDate scheduleDate;

	// 予定名
	private String title;

	// 詳細
	private String detail;

	// 作成日時
	private LocalDateTime createdAt;

	// 更新日時
	private LocalDateTime updatedAt;
}