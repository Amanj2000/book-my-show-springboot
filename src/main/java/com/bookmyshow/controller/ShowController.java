package com.bookmyshow.controller;

import com.bookmyshow.dto.*;
import com.bookmyshow.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/theatres/{theatreId}/audis/{audiNo}/shows")
public class ShowController {
	@Autowired
	ShowService showService;

	@GetMapping
	public List<ShowResponseDTO> getAllShows(@PathVariable int theatreId, @PathVariable int audiNo) {
		return showService.getAllShows(theatreId, audiNo);
	}

	@GetMapping("/{showId}")
	public ShowResponseDTO getShow(@PathVariable int theatreId, @PathVariable int audiNo, @PathVariable int showId) {
		return showService.getShow(theatreId, audiNo, showId);
	}

	@PostMapping
	public ResponseDTO addShow(@PathVariable int theatreId, @PathVariable int audiNo,
	                               @Valid @RequestBody ShowRequestDTO showRequestDTO) {
		return showService.addShow(theatreId, audiNo, showRequestDTO);
	}

	@PutMapping("/{showId}")
	public ResponseDTO updateShow(@PathVariable int theatreId, @PathVariable int audiNo,
	                                  @PathVariable int showId, @Valid @RequestBody ShowRequestDTO showRequestDTO) {
		return showService.updateShow(theatreId, audiNo, showId, showRequestDTO);
	}

	@DeleteMapping("/{showId}")
	public ResponseDTO deleteShow(@PathVariable int theatreId, @PathVariable int audiNo, @PathVariable int showId) {
		return showService.deleteShow(theatreId, audiNo, showId);
	}
}
