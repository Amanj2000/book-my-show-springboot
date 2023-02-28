package com.bookmyshow.service;

import com.bookmyshow.dto.BookingRequestDTO;
import com.bookmyshow.dto.BookingResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Booking;
import com.bookmyshow.model.Show;
import com.bookmyshow.model.ShowSeat;
import com.bookmyshow.model.User;
import com.bookmyshow.model.enums.SeatStatus;
import com.bookmyshow.repository.BookingRepository;
import com.bookmyshow.repository.ShowSeatRepository;
import com.bookmyshow.util.BookingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	ShowSeatRepository showSeatRepository;

	@Autowired
	BookingUtil bookingUtil;

	public List<BookingResponseDTO> getAllBookings(String email) {
		User user = bookingUtil.getUser(email);
		return bookingRepository.findByUser(user)
		                        .stream()
		                        .map(BookingResponseDTO::new)
		                        .collect(Collectors.toList());
	}

	public BookingResponseDTO getBooking(String email, int bookingId) {
		User user = bookingUtil.getUser(email);
		return bookingRepository.findByIdAndUser(bookingId, user)
		                        .map(BookingResponseDTO::new)
		                        .orElse(null);
	}

	public ResponseDTO bookShow(String email, int movieId, int showId, BookingRequestDTO bookingRequestDTO) {
		ResponseDTO responseDTO = bookingUtil.canBook(movieId, showId, bookingRequestDTO.getSeatNos());
		if(!responseDTO.isSuccess()) return responseDTO;

		User user = bookingUtil.getUser(email);
		Show show = bookingUtil.getShow(showId);

		Booking booking = new Booking();
		booking.setUser(user);
		booking.setShow(show);
		booking.setBookingTime(new Date());
		booking.setNoOfSeats(bookingRequestDTO.getSeatNos().size());
		booking.setTotalPrice(bookingUtil.calcTotalPrice(show, booking.getNoOfSeats()));
		booking.setShowSeats(bookingRequestDTO.getSeatNos()
		                                      .stream()
		                                      .map(seatNo -> {
												  ShowSeat showSeat = showSeatRepository.findByShowAndAudiSeatSeatNo(show,
					                                      seatNo).get();
												  showSeat.setSeatStatus(SeatStatus.Booked);
												  showSeat.setBooking(booking);
												  showSeatRepository.save(showSeat);
												  return showSeat;
		                                      }).collect(Collectors.toList()));
		bookingRepository.save(booking);
		return new ResponseDTO(true, String.format("show with id %d booked successfully", showId));
	}

	public ResponseDTO cancelBooking(String email, int bookingId) {
		ResponseDTO responseDTO = bookingUtil.canCancel(email, bookingId);
		if(!responseDTO.isSuccess()) return responseDTO;

		Booking booking = bookingRepository.findById(bookingId).get();
		booking.getShowSeats()
		       .forEach(showSeat -> {
				   showSeat.setSeatStatus(SeatStatus.Available);
				   showSeat.setBooking(null);
				   showSeatRepository.save(showSeat);
			   });
		bookingRepository.deleteById(bookingId);
		return new ResponseDTO(true, String.format("booking with id %d cancelled successfully", bookingId));
	}
}
