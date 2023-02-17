package com.bookmyshow.controller;

import com.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

	@Autowired
	UserRepository userRepository;

	@GetMapping
	public String home(Principal principal) {
		return String.format("Welcome, %s", userRepository.findByEmail(principal.getName()).get().getFirstName());
	}
}
