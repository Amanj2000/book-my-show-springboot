package com.bookmyshow.service;

import com.bookmyshow.model.UserDetailsImpl;
import com.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository
				.findByEmail(email)
				.map(UserDetailsImpl::new)
				.orElseThrow(() -> new UsernameNotFoundException("username not found " + email));
	}
}
