package com.bookmyshow.controller;

import com.bookmyshow.dto.TheatreRequestDTO;
import com.bookmyshow.dto.TheatreResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/theatres")
public class TheatreController {
	
	@Autowired
	TheatreService theatreService;
	
	@GetMapping
	public ResponseEntity<?> getAllTheatres() {
		List<TheatreResponseDTO> theatreResponseDTOS = theatreService.getAllTheatres();
		return new ResponseEntity<>(theatreResponseDTOS, HttpStatus.OK);
	}

	@GetMapping("/{theatreId}")
	public ResponseEntity<?> getTheatre(@PathVariable int theatreId) {
		TheatreResponseDTO theatreResponseDTO = theatreService.getTheatre(theatreId);
		return new ResponseEntity<>(theatreResponseDTO, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> addTheatre(@Valid @RequestBody TheatreRequestDTO theatreRequestDTO) {
		ResponseDTO responseDTO = theatreService.addTheatre(theatreRequestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/{theatreId}")
	public ResponseEntity<?> updateTheatre(@PathVariable int theatreId, @Valid @RequestBody TheatreRequestDTO theatreRequestDTO) {
		ResponseDTO responseDTO = theatreService.updateTheatre(theatreId, theatreRequestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{theatreId}")
	public ResponseEntity<?> deleteTheatre(@PathVariable int theatreId) {
		ResponseDTO responseDTO = theatreService.deleteTheatre(theatreId);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}
