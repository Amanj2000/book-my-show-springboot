package com.bookmyshow.service;

import com.bookmyshow.dto.LoginRequest;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.dto.SignupRequest;
import com.bookmyshow.model.User;
import com.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
public class AuthService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	public ResponseDTO signup(SignupRequest signupRequest) {
		if(userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
			return new ResponseDTO(false, "user by this email already exists");
		} else {
			User user = new User();
			user.setEmail(signupRequest.getEmail());
			user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
			user.setFirstName(signupRequest.getFirstName());
			user.setLastName(signupRequest.getLastName());
			user.setAge(signupRequest.getAge());

			userRepository.save(user);
			return new ResponseDTO(true, "user has been registered");
		}
	}

	public ResponseDTO login(HttpServletRequest req, LoginRequest loginRequest) {
		Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
		if(!userOptional.isPresent() ||
		   !passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())) {
			return new ResponseDTO(false, "invalid credentials");
		} else {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
							loginRequest.getPassword()));
			SecurityContext sc = SecurityContextHolder.getContext();
			sc.setAuthentication(authentication);
			HttpSession session = req.getSession(true);
			session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

			return new ResponseDTO(true, String.format("user %s successfully logged in",
					userOptional.get().getEmail()));
		}
	}
}
