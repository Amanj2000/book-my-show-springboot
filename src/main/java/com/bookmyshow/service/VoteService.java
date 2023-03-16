package com.bookmyshow.service;

import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.dto.VoteRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VoteService {
	@Autowired
	private RestTemplate restTemplate;

	private final static String REVIEW_SYSTEM_URL = "http://localhost:3002/movies";

	public String getVotes(int movieId) {
		String url = REVIEW_SYSTEM_URL + "/" + movieId + "/votes";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		return response.getBody();
	}

	public String getVoteForUser(int movieId, String userEmail) {
		String url = REVIEW_SYSTEM_URL + "/" + movieId + "/votes" + "/" + userEmail;
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		return response.getBody();
	}

	public ResponseDTO castVote(int movieId, String userEmail, VoteRequestDTO voteRequestDTO) {
		String url = REVIEW_SYSTEM_URL + "/" + movieId + "/votes";
		voteRequestDTO.setUserEmail(userEmail);
		ResponseEntity<ResponseDTO> response = restTemplate.postForEntity(url, voteRequestDTO, ResponseDTO.class);
		return response.getBody();
	}

	public ResponseDTO editVote(int movieId, String userEmail, VoteRequestDTO voteRequestDTO) {
		String url = REVIEW_SYSTEM_URL + "/" + movieId + "/votes" + "/" + userEmail;
		voteRequestDTO.setUserEmail(userEmail);
		ResponseEntity<ResponseDTO> response = restTemplate.exchange(url,
				HttpMethod.PUT,
				new HttpEntity<>(voteRequestDTO),
				ResponseDTO.class);
		return response.getBody();
	}

	public ResponseDTO deleteVote(int movieId, String userEmail) {
		String url = REVIEW_SYSTEM_URL + "/" + movieId + "/votes" + "/" + userEmail;
		ResponseEntity<ResponseDTO> response = restTemplate.exchange(url,
				HttpMethod.DELETE,
				HttpEntity.EMPTY,
				ResponseDTO.class);
		return response.getBody();
	}
}
