package com.bookmyshow.repository;

import com.bookmyshow.model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {
	@Query("FROM Movie WHERE TITLE LIKE '%?1%'")
	List<Movie> findByPartialMovieName(String partialMovieName);
}
