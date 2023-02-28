package com.bookmyshow.controller;

import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	SearchService searchService;

	@GetMapping(params = "keyword")
	public List<MovieResponseDTO> getByMovieName(@RequestParam String keyword) {
		return searchService.searchMovie(keyword);
	}
}
