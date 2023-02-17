package com.bookmyshow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
public class LoginRequest {
	@Email @NotBlank
	private final String email;

	@NotEmpty @Size(min = 8, message = "password must be at least 8 characters long")
	private final String password;
}
