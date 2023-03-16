package com.bookmyshow.helper.search;

import com.bookmyshow.model.Movie;
import com.bookmyshow.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchByMovieName implements ISearchMovie {

	@Autowired
	MovieRepository movieRepository;

	@Override
	public List<Movie> search(String partialMovieName) {
		return movieRepository.findByTitleContaining(partialMovieName);
	}
}
