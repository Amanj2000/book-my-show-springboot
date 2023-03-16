package com.bookmyshow.service;

import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.dto.ReviewRequestDTO;
import com.bookmyshow.dto.ReviewResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewService {
	@Autowired
	private RestTemplate restTemplate;

	private final static String REVIEW_SYSTEM_URL = "http://localhost:3002/movies";

	public List<ReviewResponseDTO> getAllReviews(int movieId) {
		String url = REVIEW_SYSTEM_URL + "/" + movieId + "/reviews";
		ResponseEntity<ReviewResponseDTO[]> response = restTemplate.getForEntity(url, ReviewResponseDTO[].class);
		return response.getBody() == null ? Collections.emptyList() : Arrays.asList(response.getBody());
	}

	public ReviewResponseDTO getReview(int movieId, String userEmail) {
		String url = REVIEW_SYSTEM_URL + "/" + movieId + "/reviews" + "/" + userEmail;
		ResponseEntity<ReviewResponseDTO> response = restTemplate.getForEntity(url, ReviewResponseDTO.class);
		return response.getBody();
	}

	public ResponseDTO addReview(int movieId, String email, ReviewRequestDTO reviewRequestDTO) {
		String url = REVIEW_SYSTEM_URL + "/" + movieId + "/reviews";
		reviewRequestDTO.setUserEmail(email);
		ResponseEntity<ResponseDTO> response = restTemplate.postForEntity(url, reviewRequestDTO,ResponseDTO.class);
		return response.getBody();
	}

	public ResponseDTO editReview(int movieId, String email, ReviewRequestDTO reviewRequestDTO) {
		String url = REVIEW_SYSTEM_URL + "/" + movieId + "/reviews" + "/" + email;
		reviewRequestDTO.setUserEmail(email);
		ResponseEntity<ResponseDTO> response = restTemplate.exchange(url,
				HttpMethod.PUT,
				new HttpEntity<>(reviewRequestDTO),
				ResponseDTO.class);
		return response.getBody();
	}

	public ResponseDTO deleteReview(int movieId, String email) {
		String url = REVIEW_SYSTEM_URL + "/" + movieId + "/reviews" + "/" + email;
		ResponseEntity<ResponseDTO> response = restTemplate.exchange(url,
				HttpMethod.DELETE,
				HttpEntity.EMPTY,
				ResponseDTO.class);
		return response.getBody();
	}
}
