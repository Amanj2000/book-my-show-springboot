package com.bookmyshow.util.search;

import com.bookmyshow.model.Movie;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SearchMovie {
	List<Movie> search(String str);
}
