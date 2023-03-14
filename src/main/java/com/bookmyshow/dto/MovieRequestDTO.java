package com.bookmyshow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequestDTO {
	private Integer id;

	@NotBlank
	private String title;

	@NotNull
	private String description;

	@NotNull @Positive
	@Digits(integer = 3, fraction = 0)
	private int duration;

	@NotBlank
	private String language;

	@NotBlank
	private String genre;

	@NotEmpty
	private List<String> cast;

	public MovieRequestDTO(String title, String description, int duration, String language, String genre, List<String> cast) {
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.language = language;
		this.genre = genre;
		this.cast = cast;
	}
}
