package com.example.demo.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

	@GetMapping("/")
	public String showMenu(
			Principal principal,
			Model model) {

		// ログインしているユーザー名を取得
		String username = principal.getName();

		// ユーザー名をHTMLへ渡す
		model.addAttribute("username", username);

		return "menu";
	}
}