package com.bookmyshow.util;

import com.bookmyshow.dto.MovieRequestDTO;
import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Actor;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.enums.Genre;
import com.bookmyshow.repository.ActorRepository;
import com.bookmyshow.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class MovieUtil {
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

	public ResponseDTO checkMovie(int movieId) {
		if(movieRepository.existsById(movieId))
			return new ResponseDTO(true, "");
		return new ResponseDTO(false, "invalid movie id");
	}

	public Movie getMovie(int movieId) {
		return movieRepository.findById(movieId).get();
	}

	public ResponseDTO checkGenre(String genre) {
		try {
			Genre.valueOf(genre.toUpperCase());
			return new ResponseDTO(true, "");
		} catch(IllegalArgumentException e) {
			return new ResponseDTO(false, String.format("invalid Genre type, select genre from %s",
					Arrays.toString(Genre.class.getEnumConstants())));
		}
	}

	public ResponseDTO canAdd(String genre) {
		return checkGenre(genre);
	}

	public ResponseDTO canUpdate(int movieId, String genre) {
		ResponseDTO responseDTO = checkMovie(movieId);
		if(!responseDTO.isSuccess()) return responseDTO;

		return checkGenre(genre);
	}

	public ResponseDTO canDelete(int movieId) {
		return checkMovie(movieId);
	}
}
