package com.bookmyshow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class AudiSeatRequestDTO {
	@NotBlank
	private String seatNo;

	@NotBlank
	private String seatType;
}
