package com.bookmyshow.controller;

import com.bookmyshow.dto.AudiRequestDTO;
import com.bookmyshow.dto.AudiResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.service.AudiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/theatres/{theatreId}/audis")
public class AudiController {

	@Autowired
	AudiService audiService;

	@GetMapping
	public List<AudiResponseDTO> getAllAudis(@PathVariable int theatreId) {
		return audiService.getAllAudis(theatreId);
	}

	@GetMapping("/{audiNo}")
	public AudiResponseDTO getAudi(@PathVariable int theatreId, @PathVariable int audiNo) {
		return audiService.getAudi(theatreId, audiNo);
	}

	@PostMapping
	public ResponseDTO addAudi(@PathVariable int theatreId,
	                           @Valid @RequestBody AudiRequestDTO audiRequestDTO) {
		return audiService.addAudi(theatreId, audiRequestDTO);
	}

	@PutMapping("/{audiNo}")
	public ResponseDTO updateAudi(@PathVariable int theatreId, @PathVariable int audiNo,
	                              @Valid @RequestBody AudiRequestDTO audiRequestDTO) {
		return audiService.updateAudi(theatreId, audiNo, audiRequestDTO);
	}

	@DeleteMapping("/{audiNo}")
	public ResponseDTO deleteAudi(@PathVariable int theatreId, @PathVariable int audiNo) {
		return audiService.deleteAudi(theatreId, audiNo);
	}
}
