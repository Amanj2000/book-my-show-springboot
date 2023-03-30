package com.bookmyshow.service;

import com.bookmyshow.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LogoutService implements LogoutHandler {

	@Autowired
	TokenRepository tokenRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String authHeader = request.getHeader("Authorization");
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}

		String jwtToken = authHeader.substring(7);
		tokenRepository.findByToken(jwtToken)
		               .ifPresent(token -> tokenRepository.delete(token));
		SecurityContextHolder.clearContext();
	}
}
