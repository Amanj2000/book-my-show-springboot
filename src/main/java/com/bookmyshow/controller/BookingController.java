package com.bookmyshow.controller;

import com.bookmyshow.dto.BookingRequestDTO;
import com.bookmyshow.dto.BookingResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class BookingController {

	@Autowired
	BookingService bookingService;

	@GetMapping("/bookings")
	public ResponseEntity<?> getAllBookings(Principal principal) {
		List<BookingResponseDTO> bookingResponseDTOS = bookingService.getAllBookings(principal.getName());
		return new ResponseEntity<>(bookingResponseDTOS, HttpStatus.OK);
	}

	@GetMapping("/bookings/{bookingId}")
	public ResponseEntity<?> getBooking(Principal principal, @PathVariable int bookingId) {
		BookingResponseDTO bookingResponseDTO = bookingService.getBooking(principal.getName(), bookingId);
		return new ResponseEntity<>(bookingResponseDTO, HttpStatus.OK);
	}

	@PostMapping("/movies/{movieId}/shows/{showId}/book")
	public ResponseEntity<?> bookShow(Principal principal, @PathVariable int movieId, @PathVariable int showId,
	                            @Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
		ResponseDTO responseDTO = bookingService.bookShow(principal.getName(), movieId, showId, bookingRequestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/bookings/{bookingId}")
	public ResponseEntity<?> cancelBooking(Principal principal, @PathVariable int bookingId) {
		ResponseDTO responseDTO = bookingService.cancelBooking(principal.getName(), bookingId);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}
