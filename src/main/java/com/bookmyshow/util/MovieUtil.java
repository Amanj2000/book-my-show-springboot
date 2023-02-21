package com.bookmyshow.util;

import com.bookmyshow.dto.MovieRequestDTO;
import com.bookmyshow.model.Actor;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.enums.Genre;
import com.bookmyshow.repository.ActorRepository;

import java.util.stream.Collectors;

public class MovieUtil {
	public static void mapMovieRequestToMovie(MovieRequestDTO movieRequestDTO, Movie movie, ActorRepository actorRepository) {
		movie.setTitle(movieRequestDTO.getTitle());
		movie.setDescription(movieRequestDTO.getDescription());
		movie.setDuration(movieRequestDTO.getDuration());
		movie.setLanguage(movieRequestDTO.getLanguage());
		movie.setGenre(Genre.valueOf(movieRequestDTO.getGenre().toUpperCase()));
		movie.setActors(movieRequestDTO.getCast()
		                               .stream()
		                               .map(actorName -> {
			                               if(!actorRepository.existsByName(actorName)) {
				                               actorRepository.save(new Actor(actorName));
			                               }
			                               return actorRepository.findByName(actorName).get();
		                               })
		                               .collect(Collectors.toSet()));
	}
}
