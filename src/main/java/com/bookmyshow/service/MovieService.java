package com.bookmyshow.service;

import com.bookmyshow.dto.MovieRequestDTO;
import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Movie;
import com.bookmyshow.repository.ActorRepository;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.helper.MovieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieService {
	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ActorRepository actorRepository;

	@Autowired
	private MovieHelper movieHelper;

	@Autowired
	private KafkaTemplate<Integer, MovieRequestDTO> movieProducer;

	public List<MovieResponseDTO> getAllMovies() {
		List<MovieResponseDTO> movies = new ArrayList<>();
		movieRepository.findAll()
		               .forEach(movie -> movies.add(new MovieResponseDTO(movie)));
		return movies;
	}

	public MovieResponseDTO getMovie(int movieId) {
		Movie movie = movieHelper.getMovie(movieId);
		return new MovieResponseDTO(movie);
	}

	public ResponseDTO addMovie(MovieRequestDTO movieRequestDTO) {
		movieHelper.canAdd(movieRequestDTO.getGenre());

		Movie movie = new Movie();
		movieHelper.mapMovieRequestToMovie(movieRequestDTO, movie);
		movieRepository.save(movie);
		movieRequestDTO.setId(movie.getId());
		movieProducer.send("movies", movieRequestDTO);
		return new ResponseDTO(String.format("movie %s added successfully", movie.getTitle()));
	}

	public ResponseDTO updateMovie(int movieId, MovieRequestDTO movieRequestDTO) {
		movieHelper.canUpdate(movieRequestDTO.getGenre());

		Movie movie = movieHelper.getMovie(movieId);
		movieHelper.mapMovieRequestToMovie(movieRequestDTO, movie);
		movieRepository.save(movie);
		return new ResponseDTO(String.format("movie %s updated successfully", movie.getTitle()));
	}

	public ResponseDTO deleteMovie(int movieId) {
		Movie movie = movieHelper.getMovie(movieId);
		movieRepository.delete(movie);
		return new ResponseDTO(String.format("movie %s deleted successfully", movie.getTitle()));
	}
}
