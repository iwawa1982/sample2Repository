package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	public static void main(String[]args) {
		
		//BCryptを使う準備
		BCryptPasswordEncoder encoder =new BCryptPasswordEncoder()	;
		
		//ハッシュ化したパスワード
		String password ="testpass";
		
		//パスワードをハッシュ化
		String encodePassword =encoder.encode(password);
		
		System.out.println(encodePassword);
		
	}
}
