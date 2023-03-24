package com.bookmyshow.controller;

import com.bookmyshow.dto.VoteRequestDTO;
import com.bookmyshow.feignClient.VoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/movies/{movieId}/votes")
public class VoteController {
	@Autowired
	VoteClient voteClient;

	@GetMapping
	public ResponseEntity<?> getVotes(@PathVariable int movieId) {
		return voteClient.getVotes(movieId);
	}

	@GetMapping("/{userEmail}")
	public ResponseEntity<?> getVoteForUser(@PathVariable int movieId, @PathVariable String userEmail) {
		return voteClient.getVoteForUser(movieId, userEmail);
	}

	@PostMapping
	public ResponseEntity<?> castVote(@PathVariable int movieId, Principal principal,
	                                  @Valid @RequestBody VoteRequestDTO voteRequestDTO) {
		voteRequestDTO.setUserEmail(principal.getName());
		return voteClient.castVote(movieId, voteRequestDTO);
	}

	@PutMapping
	public ResponseEntity<?> editVote(@PathVariable int movieId, Principal principal, @Valid @RequestBody VoteRequestDTO voteRequestDTO) {
		voteRequestDTO.setUserEmail(principal.getName());
		return voteClient.editVote(movieId, principal.getName(), voteRequestDTO);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteVote(@PathVariable int movieId, Principal principal) {
		return voteClient.deleteVote(movieId, principal.getName());
	}
}
