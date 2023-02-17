package com.bookmyshow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDTO {
	private final boolean success;
	private final String message;
}
