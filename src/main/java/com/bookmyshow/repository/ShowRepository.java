package com.bookmyshow.repository;

import com.bookmyshow.model.Audi;
import com.bookmyshow.model.Show;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends CrudRepository<Show, Integer> {
	List<Show> findByAudi(Audi audi);

	boolean existsByIdAndAudi(int showId, Audi audi);
}
