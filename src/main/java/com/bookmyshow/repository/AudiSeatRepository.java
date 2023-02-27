package com.bookmyshow.repository;

import com.bookmyshow.model.Audi;
import com.bookmyshow.model.AudiSeat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AudiSeatRepository extends CrudRepository<AudiSeat, Integer> {
	List<AudiSeat> findByAudi(Audi audi);

	Optional<AudiSeat> findByAudiAndSeatNo(Audi audi, String seatNo);

	int countByAudi(Audi audi);
}
