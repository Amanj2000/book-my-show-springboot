package com.bookmyshow.service;

import com.bookmyshow.dto.LoginRequest;
import com.bookmyshow.dto.LoginResponse;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.dto.SignupRequest;
import com.bookmyshow.model.Token;
import com.bookmyshow.model.User;
import com.bookmyshow.model.UserDetailsImpl;
import com.bookmyshow.repository.TokenRepository;
import com.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public ResponseDTO signup(SignupRequest signupRequest) {
		if(userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
			throw new IllegalArgumentException("user by this email already exists");
		}
		User user = new User();
		user.setEmail(signupRequest.getEmail());
		user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		user.setFirstName(signupRequest.getFirstName());
		user.setLastName(signupRequest.getLastName());
		user.setAge(signupRequest.getAge());

		userRepository.save(user);
		return new ResponseDTO("user has been registered");
	}

	public LoginResponse login(HttpServletRequest req, LoginRequest loginRequest) {
		Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
		if(userOptional.isPresent() &&
		   passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			String token = jwtService.generateToken(userOptional.map(UserDetailsImpl::new).get());
			Token jwtToken = Token.builder()
			                      .token(token)
			                      .user(userOptional.get())
			                      .build();
			tokenRepository.findByUser(userOptional.get())
			               .ifPresent(t -> tokenRepository.delete(t));
			tokenRepository.save(jwtToken);

			return LoginResponse.builder()
			                    .message(String.format("user %s successfully logged in.", userOptional.get().getEmail()))
			                    .token(token)
			                    .build();
		}
		throw new IllegalArgumentException("invalid credentials");
	}
}
