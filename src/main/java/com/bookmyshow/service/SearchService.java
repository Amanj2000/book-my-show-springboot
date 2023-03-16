package com.bookmyshow.service;

import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.helper.search.ISearchMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Thread.sleep;

@Service
public class SearchService {
	@Autowired
	private List<ISearchMovie> searchMovies;

	@Autowired
	private KafkaTemplate<String, String> searchProducer;

	private final Map<String, List<MovieResponseDTO>> searchResult = new HashMap<>();

	public List<MovieResponseDTO> searchMovie(String query) {
		searchProducer.send("search_query", query);

		try {
			while (!searchResult.containsKey(query)) sleep(100);
		} catch(InterruptedException e) {
			System.out.println("Error while fetching search result: " + e);
		}

		List<MovieResponseDTO> result = searchResult.get(query);
		searchResult.remove(query);
		return result;
	}

	public void addResult(String query, List<MovieResponseDTO> result) {
		searchResult.put(query, result);
	}
}
