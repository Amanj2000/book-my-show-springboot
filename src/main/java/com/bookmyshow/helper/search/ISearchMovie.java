package com.bookmyshow.helper.search;

import com.bookmyshow.model.Movie;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ISearchMovie {
	List<Movie> search(String str);
}
