package com.bookmyshow.controller;

import com.bookmyshow.dto.ShowResponseDTO;
import com.bookmyshow.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<ShowResponseDTO> getAllShows(@PathVariable int movieId) {
		return showService.getAllShows(movieId);
	}

	@GetMapping("/{showId}")
	public ShowResponseDTO getShow(@PathVariable int movieId, @PathVariable int showId) {
		return showService.getShow(movieId, showId);
	}
}
