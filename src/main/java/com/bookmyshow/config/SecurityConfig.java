package com.bookmyshow.config;

import com.bookmyshow.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	private final static String DATABASE_URL = "/h2-console";
	private final static String SIGNUP_URL = "/signup";
	private final static String LOGIN_URL = "/login";
	private final static String LOGOUT_URL = "/logout";
	private final static String[] PUBLIC_URLS = {DATABASE_URL + "/**", SIGNUP_URL, LOGIN_URL};
	private final static String COOKIE_NAME = "JSESSIONID";

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@Bean
	public AuthenticationManager authManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder);
		authProvider.setUserDetailsService(userDetailsService);
		return new ProviderManager(authProvider);
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.cors().and()
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeRequests(auth -> auth
						.antMatchers(PUBLIC_URLS).permitAll()
						.anyRequest().authenticated()
				)
				.userDetailsService(userDetailsServiceImpl)
				.headers(headers -> headers.frameOptions().sameOrigin())
				.logout(logout -> logout
						.logoutUrl(LOGOUT_URL)
						.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
						.deleteCookies(COOKIE_NAME))
				.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
