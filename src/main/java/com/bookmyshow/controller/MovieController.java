package com.bookmyshow.controller;

import com.bookmyshow.dto.MovieRequestDTO;
import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@GetMapping
	public ResponseEntity<?> getAllMovies() {
		List<MovieResponseDTO> movieResponseDTOS = movieService.getAllMovies();
		return new ResponseEntity<>(movieResponseDTOS, HttpStatus.OK);
	}

	@GetMapping(params = "movieName")
	public ResponseEntity<?> getByMovieName(@RequestParam String movieName) {
		List<MovieResponseDTO> movieResponseDTOS = movieService.getByMovieName(movieName);
		return new ResponseEntity<>(movieResponseDTOS, HttpStatus.OK);
	}

	@GetMapping(params = "actorName")
	public ResponseEntity<?> getByActorName(@RequestParam String actorName) {
		List<MovieResponseDTO> movieResponseDTOS = movieService.getByActorName(actorName);
		return new ResponseEntity<>(movieResponseDTOS, HttpStatus.OK);
	}

	@GetMapping(params = "genre")
	public ResponseEntity<?> getByGenre(@RequestParam String genre) {
		List<MovieResponseDTO> movieResponseDTOS = movieService.getByGenre(genre);
		return new ResponseEntity<>(movieResponseDTOS, HttpStatus.OK);
	}

	@GetMapping("/{movieId}")
	public ResponseEntity<?> getMovie(@PathVariable int movieId) {
		MovieResponseDTO movieResponseDTO = movieService.getMovie(movieId);
		return new ResponseEntity<>(movieResponseDTO, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> addMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {
		ResponseDTO responseDTO = movieService.addMovie(movieRequestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/{movieId}")
	public ResponseEntity<?> updateMovie(@PathVariable int movieId, @Valid @RequestBody MovieRequestDTO movieRequestDTO) {
		ResponseDTO responseDTO = movieService.updateMovie(movieId, movieRequestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{movieId}")
	public ResponseEntity<?> deleteMovie(@PathVariable int movieId) {
		ResponseDTO responseDTO = movieService.deleteMovie(movieId);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}
