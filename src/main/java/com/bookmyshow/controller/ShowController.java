package com.bookmyshow.controller;

import com.bookmyshow.dto.*;
import com.bookmyshow.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/theatres/{theatreId}/audis/{audiNo}/shows")
public class ShowController {
	@Autowired
	ShowService showService;

	@GetMapping
	public ResponseEntity<?> getAllShows(@PathVariable int theatreId, @PathVariable int audiNo) {
		List<ShowResponseDTO> showResponseDTOS = showService.getAllShows(theatreId, audiNo);
		return new ResponseEntity<>(showResponseDTOS, HttpStatus.OK);
	}

	@GetMapping("/{showId}")
	public ResponseEntity<?> getShow(@PathVariable int theatreId, @PathVariable int audiNo, @PathVariable int showId) {
		ShowResponseDTO showResponseDTO = showService.getShow(theatreId, audiNo, showId);
		return new ResponseEntity<>(showResponseDTO, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> addShow(@PathVariable int theatreId, @PathVariable int audiNo,
	                               @Valid @RequestBody ShowRequestDTO showRequestDTO) {
		ResponseDTO responseDTO = showService.addShow(theatreId, audiNo, showRequestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/{showId}")
	public ResponseEntity<?> updateShow(@PathVariable int theatreId, @PathVariable int audiNo,
	                                  @PathVariable int showId, @Valid @RequestBody ShowRequestDTO showRequestDTO) {
		ResponseDTO responseDTO = showService.updateShow(theatreId, audiNo, showId, showRequestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{showId}")
	public ResponseEntity<?> deleteShow(@PathVariable int theatreId, @PathVariable int audiNo, @PathVariable int showId) {
		ResponseDTO responseDTO = showService.deleteShow(theatreId, audiNo, showId);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}
