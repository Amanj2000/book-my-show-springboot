package com.bookmyshow.dto;

import com.bookmyshow.model.Actor;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.enums.Genre;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MovieResponseDTO {
	private final int id;
	private final String title;
	private final String description;
	private final int duration;
	private final String language;
	private final Genre genre;
	private final List<String> cast;

	public MovieResponseDTO(Movie movie) {
		this.id = movie.getId();
		this.title = movie.getTitle();
		this.description = movie.getDescription();
		this.duration = movie.getDuration();
		this.language = movie.getLanguage();
		this.genre = movie.getGenre();
		this.cast = movie.getActors().stream()
				.map(Actor::getName)
				.collect(Collectors.toList());
	}
}
