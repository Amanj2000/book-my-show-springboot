package com.bookmyshow.service;

import com.bookmyshow.dto.BookingRequestDTO;
import com.bookmyshow.dto.BookingResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Booking;
import com.bookmyshow.model.Show;
import com.bookmyshow.model.User;
import com.bookmyshow.model.enums.SeatStatus;
import com.bookmyshow.repository.BookingRepository;
import com.bookmyshow.helper.BookingHelper;
import com.bookmyshow.repository.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private ShowSeatRepository showSeatRepository;

	@Autowired
	private BookingHelper bookingHelper;

	public List<BookingResponseDTO> getAllBookings(String email) {
		User user = bookingHelper.getUser(email);
		return bookingRepository.findByUser(user)
		                        .stream()
		                        .map(BookingResponseDTO::new)
		                        .collect(Collectors.toList());
	}

	public BookingResponseDTO getBooking(String email, int bookingId) {
		Booking booking = bookingHelper.getBooking(email, bookingId);
		return new BookingResponseDTO(booking);
	}

	public ResponseDTO bookShow(String email, int movieId, int showId, BookingRequestDTO bookingRequestDTO) {
		bookingHelper.canBook(movieId, showId, bookingRequestDTO.getSeatNos());

		User user = bookingHelper.getUser(email);
		Show show = bookingHelper.getShow(movieId, showId);

		Booking booking = new Booking();
		booking.setUser(user);
		bookingHelper.mapBookingRequestToBooking(bookingRequestDTO, booking, show);
		bookingRepository.save(booking);
		return new ResponseDTO(String.format("show with id %d booked successfully", showId));
	}

	public ResponseDTO cancelBooking(String email, int bookingId) {
		Booking booking = bookingHelper.getBooking(email, bookingId);
		booking.getShowSeats()
		       .forEach(showSeat -> {
				   showSeat.setSeatStatus(SeatStatus.Available);
				   showSeat.setBooking(null);
				   showSeatRepository.save(showSeat);
			   });
		bookingRepository.deleteById(bookingId);
		return new ResponseDTO(String.format("booking with id %d cancelled successfully", bookingId));
	}
}
