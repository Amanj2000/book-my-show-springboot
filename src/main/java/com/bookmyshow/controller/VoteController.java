package com.bookmyshow.controller;

import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.dto.VoteRequestDTO;
import com.bookmyshow.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/movies/{movieId}/votes")
public class VoteController {
	@Autowired
	VoteService voteService;

	@GetMapping
	public ResponseEntity<?> getVotes(@PathVariable int movieId) {
		String votes = voteService.getVotes(movieId);
		return new ResponseEntity<>(votes, HttpStatus.OK);
	}

	@GetMapping("/{userEmail}")
	public ResponseEntity<?> getVoteForUser(@PathVariable int movieId, @PathVariable String userEmail) {
		String voteValue = voteService.getVoteForUser(movieId, userEmail);
		return new ResponseEntity<>(voteValue, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> castVote(@PathVariable int movieId, Principal principal,
	                                  @Valid @RequestBody VoteRequestDTO voteRequestDTO) {
		ResponseDTO responseDTO = voteService.castVote(movieId, principal.getName(), voteRequestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> editVote(@PathVariable int movieId, Principal principal, @Valid @RequestBody VoteRequestDTO voteRequestDTO) {
		ResponseDTO responseDTO = voteService.editVote(movieId, principal.getName(), voteRequestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteVote(@PathVariable int movieId, Principal principal) {
		ResponseDTO responseDTO = voteService.deleteVote(movieId, principal.getName());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}
