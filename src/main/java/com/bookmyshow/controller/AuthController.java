package com.bookmyshow.controller;

import com.bookmyshow.dto.LoginRequest;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.dto.SignupRequest;
import com.bookmyshow.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
		ResponseDTO responseDTO = authService.signup(signupRequest);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(HttpServletRequest req, @Valid @RequestBody LoginRequest loginRequest) {
		ResponseDTO responseDTO = authService.login(req, loginRequest);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}
