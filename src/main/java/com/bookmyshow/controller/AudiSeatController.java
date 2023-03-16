package com.bookmyshow.controller;

import com.bookmyshow.dto.*;
import com.bookmyshow.service.AudiSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/theatres/{theatreId}/audis/{audiNo}/audiSeats")
public class AudiSeatController {

	@Autowired
	private AudiSeatService audiSeatService;

	@GetMapping
	public ResponseEntity<?> getAllAudiSeats(@PathVariable int theatreId,
	                                                                @PathVariable int audiNo) {
		List<AudiSeatResponseDTO> audiSeatResponseDTOS = audiSeatService.getAllAudiSeats(theatreId, audiNo);
		return new ResponseEntity<>(audiSeatResponseDTOS, HttpStatus.OK);
	}

	@GetMapping("/{seatNo}")
	public ResponseEntity<?> getAudiSeat(@PathVariable int theatreId, @PathVariable int audiNo, @PathVariable String seatNo) {
		AudiSeatResponseDTO audiSeatResponseDTO = audiSeatService.getAudiSeat(theatreId, audiNo, seatNo);
		return new ResponseEntity<>(audiSeatResponseDTO, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> addAudiSeat(@PathVariable int theatreId, @PathVariable int audiNo,
	                           @Valid @RequestBody AudiSeatRequestDTO audiSeatRequestDTO) {
		ResponseDTO responseDTO = audiSeatService.addAudiSeat(theatreId, audiNo, audiSeatRequestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/{seatNo}")
	public ResponseEntity<?> updateAudiSeat(@PathVariable int theatreId, @PathVariable int audiNo,
	                                  @PathVariable String seatNo, @Valid @RequestBody AudiSeatRequestDTO audiSeatRequestDTO) {
		ResponseDTO responseDTO = audiSeatService.updateAudiSeat(theatreId, audiNo, seatNo, audiSeatRequestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{seatNo}")
	public ResponseEntity<?> deleteAudiSeat(@PathVariable int theatreId, @PathVariable int audiNo, @PathVariable String seatNo) {
		ResponseDTO responseDTO = audiSeatService.deleteAudiSeat(theatreId, audiNo, seatNo);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}
