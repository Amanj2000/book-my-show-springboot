package com.bookmyshow.controller;

import com.bookmyshow.dto.MovieRequestDTO;
import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	MovieService movieService;

	@GetMapping
	public List<MovieResponseDTO> getAllMovies() {
		return movieService.getAllMovies();
	}

	@GetMapping(params = "movieName")
	public List<MovieResponseDTO> getByMovieName(@RequestParam String movieName) {
		return movieService.getByMovieName(movieName);
	}

	@GetMapping(params = "actorName")
	public List<MovieResponseDTO> getByActorName(@RequestParam String actorName) {
		return movieService.getByActorName(actorName);
	}

	@GetMapping("/{movieId}")
	public MovieResponseDTO getMovie(@PathVariable int movieId) {
		return movieService.getMovie(movieId);
	}

	@PostMapping
	public void addMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {
		movieService.addMovie(movieRequestDTO);
	}

	@PutMapping("/{movieId}")
	public void updateMovie(@PathVariable int movieId, @Valid @RequestBody MovieRequestDTO movieRequestDTO) {
		movieService.updateMovie(movieId, movieRequestDTO);
	}

	@DeleteMapping("/{movieId}")
	public void deleteMovie(@PathVariable int movieId) {
		movieService.deleteMovie(movieId);
	}
}
