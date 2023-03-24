package com.bookmyshow.feignClient;

import com.bookmyshow.dto.VoteRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name = "vote-client", url="http://localhost:3002/movies")
public interface VoteClient {
	@GetMapping("/{movieId}/votes")
	ResponseEntity<?> getVotes(@PathVariable int movieId);

	@GetMapping("/{movieId}/votes/{userEmail}")
	ResponseEntity<?> getVoteForUser(@PathVariable int movieId, @PathVariable String userEmail);

	@PostMapping("/{movieId}/votes")
	ResponseEntity<?> castVote(@PathVariable int movieId, @Valid @RequestBody VoteRequestDTO voteRequestDTO);

	@PutMapping("/{movieId}/votes/{userEmail}")
	ResponseEntity<?> editVote(@PathVariable int movieId, @PathVariable String userEmail,
	                           @Valid @RequestBody VoteRequestDTO voteRequestDTO);

	@DeleteMapping("/{movieId}/votes/{userEmail}")
	ResponseEntity<?> deleteVote(@PathVariable int movieId, @PathVariable String userEmail);
}
