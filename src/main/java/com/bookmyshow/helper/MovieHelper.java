package com.bookmyshow.helper;

import com.bookmyshow.dto.MovieRequestDTO;
import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.model.Actor;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.enums.Genre;
import com.bookmyshow.repository.ActorRepository;
import com.bookmyshow.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class MovieHelper {
	@Autowired
	MovieRepository movieRepository;

	@Autowired
	ActorRepository actorRepository;

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

	public Movie getMovie(int movieId) {
		if(!movieRepository.existsById(movieId))
			throw new EntityNotFoundException("invalid movie id");
		return movieRepository.findById(movieId).get();
	}

	private void checkGenre(String genre) {
		try {
			Genre.valueOf(genre.toUpperCase());
		} catch(IllegalArgumentException e) {
			throw new IllegalArgumentException(String.format("invalid Genre type, select genre from %s",
					Arrays.toString(Genre.class.getEnumConstants())));
		}
	}

	public void canAdd(String genre) {
		checkGenre(genre);
	}

	public void canUpdate(String genre) {
		checkGenre(genre);
	}
}
