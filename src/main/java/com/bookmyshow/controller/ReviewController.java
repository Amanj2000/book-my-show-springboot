package com.bookmyshow.controller;

import com.bookmyshow.dto.ReviewRequestDTO;
import com.bookmyshow.feignClient.ReviewClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/movies/{movieId}/reviews")
public class ReviewController {
	@Autowired
	ReviewClient reviewClient;

	@GetMapping
	public ResponseEntity<?> getAllReviews(@PathVariable int movieId) {
		return reviewClient.getAllReviews(movieId);
	}

	@GetMapping("/{userEmail}")
	public ResponseEntity<?> getReview(@PathVariable int movieId, @PathVariable String userEmail) {
		return reviewClient.getReview(movieId, userEmail);
	}

	@PostMapping
	public ResponseEntity<?> addReview(@PathVariable int movieId, Principal principal,
	                                   @Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
		reviewRequestDTO.setUserEmail(principal.getName());
		return reviewClient.addReview(movieId, reviewRequestDTO);
	}

	@PutMapping
	public ResponseEntity<?> editReview(@PathVariable int movieId, Principal principal, @Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
		reviewRequestDTO.setUserEmail(principal.getName());
		return reviewClient.editReview(movieId, principal.getName(), reviewRequestDTO);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteReview(@PathVariable int movieId, Principal principal) {
		return reviewClient.deleteReview(movieId, principal.getName());
	}
}
