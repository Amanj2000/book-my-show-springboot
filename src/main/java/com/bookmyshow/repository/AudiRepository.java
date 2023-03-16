package com.bookmyshow.repository;

import com.bookmyshow.model.Audi;
import com.bookmyshow.model.Theatre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AudiRepository extends CrudRepository<Audi, Integer> {
	List<Audi> findByTheatre(Theatre theatre);

	Optional<Audi> findByAudiNoAndTheatre(int audiNo, Theatre theatre);

	boolean existsByAudiNoAndTheatre(int audiNo, Theatre theatre);
}
