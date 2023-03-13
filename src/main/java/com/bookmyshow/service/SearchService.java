package com.bookmyshow.service;

import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.model.Movie;
import com.bookmyshow.helper.search.ISearchMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SearchService {

	@Autowired
	private List<ISearchMovie> searchMovies;

	public List<MovieResponseDTO> searchMovie(String content) {
		Set<Movie> movies = new HashSet<>();
		for(ISearchMovie searchMovie: searchMovies) {
			movies.addAll(searchMovie.search(content));
		}
		return movies.stream()
		             .map(MovieResponseDTO::new)
		             .collect(Collectors.toList());
	}
}
