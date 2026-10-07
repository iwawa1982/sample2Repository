package com.example.demo.controller;


import jakarta.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.repository.UserMapper;


@Controller
public class UserController {

	
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	
	
	public UserController(
			UserMapper userMapper,
			PasswordEncoder passwordEncoder) {
		
		this.userMapper =userMapper;
		this.passwordEncoder =passwordEncoder;
		
	}
	
	
	//ユーザー登録画面を表示
	
	@GetMapping("/users/form")
	public String showForm(Model model) {
		
		User user =new User();
		
		model.addAttribute("user", user);
		
		return "user/form";
	}
	
	//ユーザーを登録
	
	@PostMapping("/users/register")
	public String register(
			@Valid User user,
			BindingResult bindingResult,
			Model model) {
		
		//入力エラーがある場合
		if(bindingResult.hasErrors()) {
			return "user/form";
			
		}
		
		//同じユーザー名が登録されているか確認
		User existingUser =
				userMapper.selectByUsername(user.getUsername());
		
		//すでに登録されていた場合
		if(existingUser != null) {
			
			
			
			model.addAttribute(
					"errorMessage","このユーザー名はすでに使用されています");
			
			return "user/form";
		}
		
		
		
		//パスワードをBCryptでハッシュ化
		String encodedPassword =
				passwordEncoder.encode(user.getPassword());
		
		//ハッシュ化したパスワードをUserにセット
		user.setPassword(encodedPassword);
		
		//DBに登録
		userMapper.insert(user);
		
		return "redirect:/";
		
		
	}
	
}
