package com.bookmyshow.service;

import com.bookmyshow.dto.MovieResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SearchService {
	@Autowired
	private RestTemplate restTemplate;

	private final static String GLOBAL_SEARCH_URL = "http://localhost:3001/search";

	public List<MovieResponseDTO> searchMovie(String content) {
		String url = GLOBAL_SEARCH_URL + "?content=" +  content;
		ResponseEntity<MovieResponseDTO[]> response = restTemplate.getForEntity(url, MovieResponseDTO[].class);
		return response.getBody() == null ? Collections.emptyList() : Arrays.asList(response.getBody());
	}
}
