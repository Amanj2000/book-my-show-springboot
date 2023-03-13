package com.bookmyshow.controller;

import com.bookmyshow.dto.ShowResponseDTO;
import com.bookmyshow.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies/{movieId}/shows")
public class MovieShowController {
	@Autowired
	ShowService showService;

	@GetMapping
	public ResponseEntity<?> getAllShows(@PathVariable int movieId) {
		List<ShowResponseDTO> showResponseDTOS = showService.getAllShows(movieId);
		return new ResponseEntity<>(showResponseDTOS, HttpStatus.OK);
	}

	@GetMapping("/{showId}")
	public ResponseEntity<?> getShow(@PathVariable int movieId, @PathVariable int showId) {
		ShowResponseDTO showResponseDTO = showService.getShow(movieId, showId);
		return new ResponseEntity<>(showResponseDTO, HttpStatus.OK);
	}
}
