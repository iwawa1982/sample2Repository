package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.User;

@Mapper
public interface UserMapper {

	// ユーザー名を指定してユーザーを1件取得する
	@Select("""
		SELECT
			id,
			username,
			password
		FROM users
		WHERE username = #{username}
		""")
	User selectByUsername(String username);

}