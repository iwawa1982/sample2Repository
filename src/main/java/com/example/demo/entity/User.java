package com.example.demo.entity;

import jakarta.validation.constraints.NotBlank;

public class User {

private Integer id;

@NotBlank(message ="ユーザー名を入力してください")
private String username;

@NotBlank(message ="パスワードを入力してください")
private String password;


public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

}
