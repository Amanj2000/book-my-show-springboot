package com.bookmyshow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
public class AudiRequestDTO {
	private String temp; //why?

	@NotNull @Positive
	@Digits(integer = 3, fraction = 0)
	private int audiNo;
}
