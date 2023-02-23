package com.bookmyshow.controller;

import com.bookmyshow.dto.TheatreRequestDTO;
import com.bookmyshow.dto.TheatreResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/theatres")
public class TheatreController {
	
	@Autowired
	TheatreService theatreService;
	
	@GetMapping
	public List<TheatreResponseDTO> getAllTheatres() {
		return theatreService.getAllTheatres();
	}

	@GetMapping("/{theatreId}")
	public TheatreResponseDTO getTheatre(@PathVariable int theatreId) {
		return theatreService.getTheatre(theatreId);
	}

	@PostMapping
	public ResponseDTO addTheatre(@Valid @RequestBody TheatreRequestDTO theatreRequestDTO) {
		return theatreService.addTheatre(theatreRequestDTO);
	}

	@PutMapping("/{theatreId}")
	public ResponseDTO updateTheatre(@PathVariable int theatreId, @Valid @RequestBody TheatreRequestDTO theatreRequestDTO) {
		return theatreService.updateTheatre(theatreId, theatreRequestDTO);
	}

	@DeleteMapping("/{theatreId}")
	public ResponseDTO deleteTheatre(@PathVariable int theatreId) {
		return theatreService.deleteTheatre(theatreId);
	}
}
