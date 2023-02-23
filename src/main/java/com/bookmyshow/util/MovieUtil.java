package com.bookmyshow.util;

import com.bookmyshow.dto.MovieRequestDTO;
import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.model.Actor;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.enums.Genre;
import com.bookmyshow.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class MovieUtil {

	@Autowired
	ActorRepository actorRepository;

	public boolean isGenreValid(String genre) {
		try {
			Genre.valueOf(genre.toUpperCase());
			return true;
		} catch(IllegalArgumentException e) {
			return false;
		}
	}

	public List<MovieResponseDTO> toMovieResponseDTOS(List<Movie> movies) {
		return movies.stream()
		             .map(MovieResponseDTO::new)
		             .collect(Collectors.toList());
	}

	public void mapMovieRequestToMovie(MovieRequestDTO movieRequestDTO, Movie movie) {
		movie.setTitle(movieRequestDTO.getTitle());
		movie.setDescription(movieRequestDTO.getDescription());
		movie.setDuration(movieRequestDTO.getDuration());
		movie.setLanguage(movieRequestDTO.getLanguage());
		movie.setGenre(Genre.valueOf(movieRequestDTO.getGenre().toUpperCase()));
		movie.setActors(createActorSet(movieRequestDTO.getCast()));
	}

	private List<Actor> createActorSet(List<String> cast) {
		return cast.stream()
		           .map(actorName -> {
					   if(!actorRepository.existsByName(actorName)) {
						   actorRepository.save(new Actor(actorName));
					   }
					   return actorRepository.findByName(actorName).get();
				   })
		           .collect(Collectors.toList());
	}
}
