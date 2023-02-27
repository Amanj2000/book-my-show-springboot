package com.bookmyshow.controller;

import com.bookmyshow.dto.*;
import com.bookmyshow.service.AudiSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/theatres/{theatreId}/audis/{audiNo}/audiSeats")
public class AudiSeatController {

	@Autowired
	AudiSeatService audiSeatService;

	@GetMapping
	public List<AudiSeatResponseDTO> getAllAudiSeats(@PathVariable int theatreId, @PathVariable int audiNo) {
		return audiSeatService.getAllAudiSeats(theatreId, audiNo);
	}

	@GetMapping("/{seatNo}")
	public AudiSeatResponseDTO getAudiSeat(@PathVariable int theatreId, @PathVariable int audiNo, @PathVariable String seatNo) {
		return audiSeatService.getAudiSeat(theatreId, audiNo, seatNo);
	}

	@PostMapping
	public ResponseDTO addAudiSeat(@PathVariable int theatreId, @PathVariable int audiNo,
	                           @Valid @RequestBody AudiSeatRequestDTO audiSeatRequestDTO) {
		return audiSeatService.addAudiSeat(theatreId, audiNo, audiSeatRequestDTO);
	}

	@PutMapping("/{seatNo}")
	public ResponseDTO updateAudiSeat(@PathVariable int theatreId, @PathVariable int audiNo,
	                                  @PathVariable String seatNo, @Valid @RequestBody AudiSeatRequestDTO audiSeatRequestDTO) {
		return audiSeatService.updateAudiSeat(theatreId, audiNo, seatNo, audiSeatRequestDTO);
	}

	@DeleteMapping("/{seatNo}")
	public ResponseDTO deleteAudiSeat(@PathVariable int theatreId, @PathVariable int audiNo, @PathVariable String seatNo) {
		return audiSeatService.deleteAudiSeat(theatreId, audiNo, seatNo);
	}
}
