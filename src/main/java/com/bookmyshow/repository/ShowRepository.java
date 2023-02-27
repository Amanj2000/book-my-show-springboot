package com.bookmyshow.repository;

import com.bookmyshow.model.Audi;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.Show;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends CrudRepository<Show, Integer> {
	List<Show> findByAudi(Audi audi);

	List<Show> findByMovie(Movie movie);

	boolean existsByIdAndAudi(int showId, Audi audi);

	boolean existsByIdAndMovie(int showId, Movie movie);
}
