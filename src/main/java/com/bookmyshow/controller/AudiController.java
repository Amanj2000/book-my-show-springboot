package com.bookmyshow.controller;

import com.bookmyshow.dto.AudiRequestDTO;
import com.bookmyshow.dto.AudiResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.service.AudiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/theatres/{theatreId}/audis")
public class AudiController {

	@Autowired
	AudiService audiService;

	@GetMapping
	public ResponseEntity<?> getAllAudis(@PathVariable int theatreId) {
		List<AudiResponseDTO> audiResponseDTOS = audiService.getAllAudis(theatreId);
		return new ResponseEntity<>(audiResponseDTOS, HttpStatus.OK);
	}

	@GetMapping("/{audiNo}")
	public ResponseEntity<?> getAudi(@PathVariable int theatreId, @PathVariable int audiNo) {
		AudiResponseDTO audiResponseDTO =  audiService.getAudi(theatreId, audiNo);
		return new ResponseEntity<>(audiResponseDTO, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> addAudi(@PathVariable int theatreId,
	                           @Valid @RequestBody AudiRequestDTO audiRequestDTO) {
		ResponseDTO responseDTO = audiService.addAudi(theatreId, audiRequestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/{audiNo}")
	public ResponseEntity<?> updateAudi(@PathVariable int theatreId, @PathVariable int audiNo,
	                              @Valid @RequestBody AudiRequestDTO audiRequestDTO) {
		ResponseDTO responseDTO = audiService.updateAudi(theatreId, audiNo, audiRequestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{audiNo}")
	public ResponseEntity<?> deleteAudi(@PathVariable int theatreId, @PathVariable int audiNo) {
		ResponseDTO responseDTO = audiService.deleteAudi(theatreId, audiNo);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}
