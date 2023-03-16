package com.bookmyshow.dto.deserializer;

import com.bookmyshow.dto.MovieResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class MovieDTODeserializer implements Deserializer<List<MovieResponseDTO>> {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<MovieResponseDTO> deserialize(String s, byte[] bytes) {
		try {
			if(bytes == null) return null;
			return objectMapper.readValue(new String(bytes, StandardCharsets.UTF_8),
					objectMapper.getTypeFactory().constructCollectionType(List.class, MovieResponseDTO.class));
		} catch (JsonProcessingException e) {
			throw new SerializationException("Error while de-serializing Movie " + e.toString());
		}
	}

}
