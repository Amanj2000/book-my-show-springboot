package com.bookmyshow.dto;

import com.bookmyshow.helper.EnumValidator;
import com.bookmyshow.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@AllArgsConstructor
public class MovieRequestDTO {
	@NotBlank
	private String title;

	@NotNull
	private String description;

	@NotNull @Positive
	@Digits(integer = 3, fraction = 0)
	private int duration;

	@NotBlank
	private String language;

	@NotNull
	@EnumValidator(enumClass = Genre.class, message = "Invalid Genre type")
	private String genre;

	@NotEmpty
	private List<String> cast;
}
