package com.bookmyshow.service;

import com.bookmyshow.dto.MovieRequestDTO;
import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.model.Movie;
import com.bookmyshow.repository.ActorRepository;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.util.MovieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	ActorRepository actorRepository;

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

	public void addMovie(MovieRequestDTO movieRequestDTO) {
		Movie movie = new Movie();
		MovieUtil.mapMovieRequestToMovie(movieRequestDTO, movie, actorRepository);
		movieRepository.save(movie);
	}

	public void updateMovie(int movieId, MovieRequestDTO movieRequestDTO) {
		Optional<Movie> movieOptional = movieRepository.findById(movieId);
		if(movieOptional.isPresent()) {
			Movie movie = movieOptional.get();
			MovieUtil.mapMovieRequestToMovie(movieRequestDTO, movie, actorRepository);
			movieRepository.save(movie);
		}
	}

	public void deleteMovie(int movieId) {
		movieRepository.deleteById(movieId);
	}
}
