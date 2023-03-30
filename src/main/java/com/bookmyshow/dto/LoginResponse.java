package com.bookmyshow.dto;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	private String message;
	private String token;
}
