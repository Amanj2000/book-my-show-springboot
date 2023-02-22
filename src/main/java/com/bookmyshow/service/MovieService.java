package com.bookmyshow.service;

import com.bookmyshow.dto.MovieRequestDTO;
import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.enums.Genre;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.util.MovieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	MovieUtil movieUtil;

	public List<MovieResponseDTO> getAllMovies() {
		List<MovieResponseDTO> movies = new ArrayList<>();
		movieRepository.findAll()
		               .forEach(movie -> movies.add(new MovieResponseDTO(movie)));
		return movies;
	}

	public List<MovieResponseDTO> getByMovieName(String partialMovieName) {
		// TODO
//		return movieRepository.findByPartialMovieName(partialMovieName);
		return null;
	}

	public List<MovieResponseDTO> getByActorName(String actorName) {
		// TODO
		return null;
	}

	public MovieResponseDTO getMovie(int movieId) {
		Optional<Movie> movie = movieRepository.findById(movieId);
		return movie.map(MovieResponseDTO::new).orElse(null);
	}

	public ResponseDTO addMovie(MovieRequestDTO movieRequestDTO) {
		if(movieUtil.isGenreValid(movieRequestDTO.getGenre())) {
			Movie movie = new Movie();
			movieUtil.mapMovieRequestToMovie(movieRequestDTO, movie);
			movieRepository.save(movie);
			return new ResponseDTO(true, String.format("movie %s added successfully", movie.getTitle()));
		}
		return new ResponseDTO(false, String.format("invalid Genre type, select genre from %s",
				Arrays.toString(Genre.class.getEnumConstants())));
	}

	public ResponseDTO updateMovie(int movieId, MovieRequestDTO movieRequestDTO) {
		if(movieUtil.isGenreValid(movieRequestDTO.getGenre())) {
			Optional<Movie> movieOptional = movieRepository.findById(movieId);
			if (movieOptional.isPresent()) {
				Movie movie = movieOptional.get();
				movieUtil.mapMovieRequestToMovie(movieRequestDTO, movie);
				movieRepository.save(movie);
				return new ResponseDTO(true, String.format("movie %s updated successfully", movie.getTitle()));
			}
			return new ResponseDTO(false, "invalid movie id");
		}
		return new ResponseDTO(false, String.format("invalid Genre type, select genre from %s",
				Arrays.toString(Genre.class.getEnumConstants())));
	}

	public ResponseDTO deleteMovie(int movieId) {
		if(movieRepository.existsById(movieId)) {
			Movie movie = movieRepository.findById(movieId).get();
			movieRepository.delete(movie);
			return new ResponseDTO(true, String.format("movie %s deleted successfully", movie.getTitle()));
		}
		return new ResponseDTO(false, "invalid movie id");
	}
}
