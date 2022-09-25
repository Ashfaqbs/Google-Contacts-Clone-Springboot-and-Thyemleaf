package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.UserDao;
import com.smart.entity.User;

public class UserDetailsServiceImpl  implements UserDetailsService{

	@Autowired
	private UserDao dao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userByUsername = this.dao.getUserByUsername(username);

		if(userByUsername==null)
		{
			throw new UsernameNotFoundException("Could not found user!!");
		}
		
		return new CustomUserDetails(userByUsername);
	}



}
