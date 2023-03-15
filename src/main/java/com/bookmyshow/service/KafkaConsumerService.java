package com.bookmyshow.service;

import com.bookmyshow.dto.MovieResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumerService {
	@Autowired
	private SearchService searchService;

	@KafkaListener(topics = "search_result", groupId = "search-result-consumer-group", containerFactory = "searchListenerContainerFactory")
	public void listenMovie(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String query,  List<MovieResponseDTO> result) {
		searchService.addResult(query, result);
	}
}
