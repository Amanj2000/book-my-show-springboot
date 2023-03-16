package com.bookmyshow.dto.serializer;

import com.bookmyshow.dto.MovieRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class MovieDTOSerializer implements Serializer<MovieRequestDTO> {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String s, MovieRequestDTO movieDTO) {
		try {
			if(movieDTO == null) return null;
			return objectMapper.writeValueAsBytes(movieDTO);
		} catch (JsonProcessingException e) {
			throw new SerializationException("Error while serializing Movie " + e);
		}
	}
}
