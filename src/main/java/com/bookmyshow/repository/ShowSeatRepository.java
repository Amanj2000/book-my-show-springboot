package com.bookmyshow.repository;

import com.bookmyshow.model.Show;
import com.bookmyshow.model.ShowSeat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends CrudRepository<ShowSeat, Integer> {
	List<ShowSeat> findByShow(Show show);

//	void deleteAllByShow(Show show);
}
