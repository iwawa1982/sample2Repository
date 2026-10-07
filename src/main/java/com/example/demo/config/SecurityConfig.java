package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) 
			throws Exception{

		http
		//ログインしていないと各画面を表示できない
		.authorizeHttpRequests(auth -> auth
				.anyRequest().authenticated()
				)

		//自分で作るログイン画面を使用する

		.formLogin(form -> form
				.loginPage("/login")
				.permitAll()
				);

		return http.build();

	}
}
