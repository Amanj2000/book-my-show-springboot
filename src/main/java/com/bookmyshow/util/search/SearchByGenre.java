package com.bookmyshow.util.search;

import com.bookmyshow.model.Movie;
import com.bookmyshow.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchByGenre implements ISearchMovie {

	@Autowired
	MovieRepository movieRepository;

	@Override
	public List<Movie> search(String genre) {
		return movieRepository.findByGenre(genre.toUpperCase());
	}
}
