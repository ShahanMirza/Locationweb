package com.learn.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
import org.springframework.stereotype.Service;

import com.learn.oauth.entity.User;
import com.learn.oauth.repository.UserRepository;

@Service(value="userDetailService")
public  class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username)  {
		// TODO Auto-generated method stub
		User user=userRepository.findByUsername(username);
		if(user ==null)
		throw new BadClientCredentialsException();
		
		new AccountStatusUserDetailsChecker().check(user);
		return user;
	}

	
}
