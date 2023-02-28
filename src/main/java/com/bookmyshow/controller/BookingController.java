package com.bookmyshow.controller;

import com.bookmyshow.dto.BookingRequestDTO;
import com.bookmyshow.dto.BookingResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class BookingController {

	@Autowired
	BookingService bookingService;

	@GetMapping("/bookings")
	List<BookingResponseDTO> getAllBookings(Principal principal) {
		return bookingService.getAllBookings(principal.getName());
	}

	@GetMapping("/bookings/{bookingId}")
	BookingResponseDTO getBooking(Principal principal, @PathVariable int bookingId) {
		return bookingService.getBooking(principal.getName(), bookingId);
	}

	@PostMapping("/movies/{movieId}/shows/{showId}/book")
	ResponseDTO bookShow(Principal principal, @PathVariable int movieId, @PathVariable int showId,
	                            @Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
		return bookingService.bookShow(principal.getName(), movieId, showId, bookingRequestDTO);
	}

	@DeleteMapping("/bookings/{bookingId}")
	ResponseDTO cancelBooking(Principal principal, @PathVariable int bookingId) {
		return bookingService.cancelBooking(principal.getName(), bookingId);
	}
}
