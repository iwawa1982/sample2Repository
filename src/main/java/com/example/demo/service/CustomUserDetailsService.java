package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserMapper userMapper;

	public CustomUserDetailsService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		// DBからユーザー名を使って検索
		User user = userMapper.selectByUsername(username);

		// ユーザーが見つからなかった場合
		if (user == null) {
			throw new UsernameNotFoundException(
					"ユーザーが見つかりません：" + username);
		}

		// Spring Securityが認証に使用するユーザー情報を作る
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getUsername())
				.password(user.getPassword())
				.roles("USER")
				.build();
	}
}