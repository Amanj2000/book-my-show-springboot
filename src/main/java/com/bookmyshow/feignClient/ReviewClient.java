package com.bookmyshow.feignClient;

import com.bookmyshow.dto.ReviewRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name = "review-client", url="http://localhost:3002/movies")
public interface ReviewClient {
	@GetMapping("/{movieId}/reviews")
	ResponseEntity<?> getAllReviews(@PathVariable int movieId);

	@GetMapping("/{movieId}/reviews/{userEmail}")
	ResponseEntity<?> getReview(@PathVariable int movieId, @PathVariable String userEmail);

	@PostMapping("/{movieId}/reviews")
	ResponseEntity<?> addReview(@PathVariable int movieId, @Valid @RequestBody ReviewRequestDTO reviewRequestDTO);

	@PutMapping("/{movieId}/reviews/{userEmail}")
	ResponseEntity<?> editReview(@PathVariable int movieId, @PathVariable String userEmail,
	                             @Valid @RequestBody ReviewRequestDTO reviewRequestDTO);

	@DeleteMapping("/{movieId}/reviews/{userEmail}")
	ResponseEntity<?> deleteReview(@PathVariable int movieId, @PathVariable String userEmail);
}
