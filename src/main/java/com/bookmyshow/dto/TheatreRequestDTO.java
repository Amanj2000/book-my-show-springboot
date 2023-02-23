package com.bookmyshow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class TheatreRequestDTO {
	@NotBlank
	private String name;

	@NotBlank
	private String address;

	@NotBlank
	private String city;

	@NotBlank
	private String state;
}
