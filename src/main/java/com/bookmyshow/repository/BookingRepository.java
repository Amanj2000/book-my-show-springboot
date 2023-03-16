package com.bookmyshow.repository;

import com.bookmyshow.model.Booking;
import com.bookmyshow.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {
	List<Booking> findByUser(User user);

	Optional<Booking> findByIdAndUser(int bookingId, User user);
}
