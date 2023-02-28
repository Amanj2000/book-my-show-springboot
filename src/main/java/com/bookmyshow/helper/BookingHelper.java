package com.bookmyshow.helper;

import com.bookmyshow.model.Booking;
import com.bookmyshow.model.Show;
import com.bookmyshow.model.User;
import com.bookmyshow.model.enums.SeatStatus;
import com.bookmyshow.repository.BookingRepository;
import com.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookingHelper {
	@Autowired
	UserRepository userRepository;

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	ShowHelper showHelper;

	public User getUser(String email) {
		return userRepository.findByEmail(email).get();
	}

	public Show getShow(int movieId, int showId) {
		return showHelper.getShow(movieId, showId);
	}

	public Booking getBooking(String email, int bookingId) {
		User user = getUser(email);
		if(!bookingRepository.findByIdAndUser(bookingId, user).isPresent())
			throw new EntityNotFoundException("invalid booking id");
		return bookingRepository.findById(bookingId).get();
	}

	private void checkSeats(Show show, List<String> seatNos) {
		Set<String> allShowSeatNos = show.getShowSeats()
		                                 .stream()
		                                 .map(showSeat -> showSeat.getAudiSeat().getSeatNo())
		                                 .collect(Collectors.toSet());

		List<String> invalidSeats = seatNos.stream()
		                                   .filter(seatNo -> !allShowSeatNos.contains(seatNo))
		                                   .collect(Collectors.toList());

		if(!invalidSeats.isEmpty())
			throw new IllegalArgumentException(String.format("invalid seats: %s", invalidSeats));
	}

	public void canBook(int movieId, int showId, List<String> seatNos) {
		Show show = getShow(movieId, showId);
		checkSeats(show, seatNos);

		Set<String> seatNoSet = new HashSet<>(seatNos);
		List<String> bookedSeats = show.getShowSeats()
		                               .stream()
		                               .filter(showSeat -> seatNoSet.contains(showSeat.getAudiSeat().getSeatNo()))
		                               .filter(showSeat -> showSeat.getSeatStatus() == SeatStatus.Booked)
		                               .map(showSeat -> showSeat.getAudiSeat().getSeatNo())
		                               .collect(Collectors.toList());

		if(!bookedSeats.isEmpty())
			throw new IllegalArgumentException(String.format("seats %s are already booked", bookedSeats));
	}

	public int calcTotalPrice(Show show, int noOfSeats) {
		int price = show.getShowSeats()
		                .get(0).getPrice();
		return price * noOfSeats;
	}
}
