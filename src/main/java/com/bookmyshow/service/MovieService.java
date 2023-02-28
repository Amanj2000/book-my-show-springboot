package com.bookmyshow.service;

import com.bookmyshow.dto.MovieRequestDTO;
import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Movie;
import com.bookmyshow.repository.ActorRepository;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.helper.MovieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieService {

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	ActorRepository actorRepository;

	@Autowired
	MovieHelper movieHelper;

	public List<MovieResponseDTO> getAllMovies() {
		List<MovieResponseDTO> movies = new ArrayList<>();
		movieRepository.findAll()
		               .forEach(movie -> movies.add(new MovieResponseDTO(movie)));
		return movies;
	}

	public List<MovieResponseDTO> getByMovieName(String partialMovieName) {
		return movieHelper.toMovieResponseDTOS(movieRepository.findByTitleContaining(partialMovieName));
	}

	public List<MovieResponseDTO> getByActorName(String actorName) {
		Set<Movie> movies = new HashSet<>();
		actorRepository.findByNameContaining(actorName)
		               .forEach(actor -> movies.addAll(actor.getMovies()));
		return movieHelper.toMovieResponseDTOS(new ArrayList<>(movies));
	}

	public List<MovieResponseDTO> getByGenre(String genre) {
		return movieHelper.toMovieResponseDTOS(movieRepository.findByGenre(genre.toUpperCase()));
	}

	public MovieResponseDTO getMovie(int movieId) {
		ResponseDTO responseDTO = movieHelper.checkMovie(movieId);
		if(!responseDTO.isSuccess()) return null;

		Movie movie = movieHelper.getMovie(movieId);
		return new MovieResponseDTO(movie);
	}

	public ResponseDTO addMovie(MovieRequestDTO movieRequestDTO) {
		ResponseDTO responseDTO = movieHelper.canAdd(movieRequestDTO.getGenre());
		if(!responseDTO.isSuccess()) return responseDTO;

		Movie movie = new Movie();
		movieHelper.mapMovieRequestToMovie(movieRequestDTO, movie);
		movieRepository.save(movie);
		return new ResponseDTO(true, String.format("movie %s added successfully", movie.getTitle()));
	}

	public ResponseDTO updateMovie(int movieId, MovieRequestDTO movieRequestDTO) {
		ResponseDTO responseDTO = movieHelper.canUpdate(movieId, movieRequestDTO.getGenre());
		if(!responseDTO.isSuccess()) return responseDTO;

		Movie movie = movieHelper.getMovie(movieId);
		movieHelper.mapMovieRequestToMovie(movieRequestDTO, movie);
		movieRepository.save(movie);
		return new ResponseDTO(true, String.format("movie %s updated successfully", movie.getTitle()));
	}

	public ResponseDTO deleteMovie(int movieId) {
		ResponseDTO responseDTO = movieHelper.canDelete(movieId);
		if(!responseDTO.isSuccess()) return responseDTO;

		Movie movie = movieHelper.getMovie(movieId);
		movieRepository.delete(movie);
		return new ResponseDTO(true, String.format("movie %s deleted successfully", movie.getTitle()));
	}
}
