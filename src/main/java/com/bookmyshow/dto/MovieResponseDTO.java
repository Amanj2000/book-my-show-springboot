package com.bookmyshow.dto;

import com.bookmyshow.model.Actor;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.enums.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
public class MovieResponseDTO {
	private int id;
	private String title;
	private String description;
	private int duration;
	private String language;
	private Genre genre;
	private List<String> cast;

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

	@Override
	public String toString() {
		return String.format("id: %d \t title: %s \t desc: %s \t duration: %d \t language: %s \t genre: %s \t cast: %s\n",
				id, title, description, duration, language, genre, cast);
	}
}
