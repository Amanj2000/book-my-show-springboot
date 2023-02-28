package com.bookmyshow.helper;

import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Show;
import com.bookmyshow.model.User;
import com.bookmyshow.model.enums.SeatStatus;
import com.bookmyshow.repository.BookingRepository;
import com.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	public ResponseDTO checkShow(int movieId, int showId) {
		return showHelper.checkShow(movieId, showId);
	}

	public Show getShow(int showId) {
		return showHelper.getShow(showId);
	}

	public ResponseDTO checkSeats(Show show, List<String> seatNos) {
		Set<String> allShowSeatNos = show.getShowSeats()
		                                 .stream()
		                                 .map(showSeat -> showSeat.getAudiSeat().getSeatNo())
		                                 .collect(Collectors.toSet());

		List<String> invalidSeats = seatNos.stream()
		                                   .filter(seatNo -> !allShowSeatNos.contains(seatNo))
		                                   .collect(Collectors.toList());

		if(invalidSeats.isEmpty())
			return new ResponseDTO(true, "");
		return new ResponseDTO(false, String.format("invalid seats: %s", invalidSeats));
	}

	public ResponseDTO canBook(int movieId, int showId, List<String> seatNos) {
		ResponseDTO responseDTO = checkShow(movieId, showId);
		if(!responseDTO.isSuccess()) return responseDTO;

		Show show = getShow(showId);
		responseDTO = checkSeats(show, seatNos);
		if(!responseDTO.isSuccess()) return responseDTO;

		Set<String> seatNoSet = new HashSet<>(seatNos);
		List<String> bookedSeats = show.getShowSeats()
		                               .stream()
		                               .filter(showSeat -> seatNoSet.contains(showSeat.getAudiSeat().getSeatNo()))
		                               .filter(showSeat -> showSeat.getSeatStatus() == SeatStatus.Booked)
		                               .map(showSeat -> showSeat.getAudiSeat().getSeatNo())
		                               .collect(Collectors.toList());

		if(bookedSeats.isEmpty()) return new ResponseDTO(true, "");
		return new ResponseDTO(false, String.format("seats %s are already booked", bookedSeats));
	}

	public int calcTotalPrice(Show show, int noOfSeats) {
		int price = show.getShowSeats()
		                .get(0).getPrice();
		return price * noOfSeats;
	}

	public ResponseDTO canCancel(String email, int bookingId) {
		User user = getUser(email);
		if(bookingRepository.findByIdAndUser(bookingId, user).isPresent())
			return new ResponseDTO(true, "");
		return new ResponseDTO(false, "invalid booking id");
	}
}
