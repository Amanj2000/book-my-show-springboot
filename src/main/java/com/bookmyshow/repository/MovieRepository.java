package com.bookmyshow.repository;

import com.bookmyshow.model.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {
	List<Movie> findByTitleContaining(String partialMovieName);
}
