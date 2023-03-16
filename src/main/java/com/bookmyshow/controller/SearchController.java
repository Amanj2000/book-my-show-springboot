package com.bookmyshow.controller;

import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	@GetMapping(params = "content")
	public ResponseEntity<?> getByMovieName(@RequestParam String content) {
		List<MovieResponseDTO> movieResponseDTOS = searchService.searchMovie(content);
		return new ResponseEntity<>(movieResponseDTOS, HttpStatus.OK);
	}
}
