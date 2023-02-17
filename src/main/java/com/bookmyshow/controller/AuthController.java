package com.bookmyshow.controller;

import com.bookmyshow.dto.LoginRequest;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.dto.SignupRequest;
import com.bookmyshow.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class AuthController {

	@Autowired
	AuthService authService;

	@PostMapping("/signup")
	public ResponseDTO signup(@Valid @RequestBody SignupRequest signupRequest) {
		return authService.signup(signupRequest);
	}

	@PostMapping("/login")
	public ResponseDTO login(HttpServletRequest req, @Valid @RequestBody LoginRequest loginRequest) {
		return authService.login(req, loginRequest);
	}
}
