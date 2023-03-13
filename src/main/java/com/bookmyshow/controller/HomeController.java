package com.bookmyshow.controller;

import com.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

	@Autowired
	UserRepository userRepository;

	@GetMapping
	public ResponseEntity<?> home(Principal principal) {
		String message = String.format("Welcome, %s", userRepository.findByEmail(principal.getName()).get().getFirstName());
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
