package com.bookmyshow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
public class SignupRequest {
	@Email @NotBlank
	private final String email;

	@NotEmpty @Size(min = 8, message = "password must be at least 8 characters long")
	private final String password;

	@NotBlank
	private final String firstName;

	@NotBlank
	private final String lastName;

	@Min(1) @Max(150)
	@Digits(integer = 3, fraction = 0)
	@NotNull(message = "must not be empty")
	private final Integer age;
}
