package com.bookmyshow.config;

import com.bookmyshow.dto.MovieRequestDTO;
import com.bookmyshow.dto.serializer.MovieDTOSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapServer;

	@Bean
	public ProducerFactory<Integer, MovieRequestDTO> movieProducerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configs.put(ProducerConfig.CLIENT_ID_CONFIG, "movie-producer");
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MovieDTOSerializer.class);
		return new DefaultKafkaProducerFactory<>(configs);
	}

	@Bean
	public KafkaTemplate<Integer, MovieRequestDTO> movieKafkaTemplate() {
		return new KafkaTemplate<>(movieProducerFactory());
	}

	@Bean
	public ProducerFactory<String, String> searchProducerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configs.put(ProducerConfig.CLIENT_ID_CONFIG, "search-query-producer");
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new DefaultKafkaProducerFactory<>(configs);
	}

	@Bean
	public KafkaTemplate<String, String> searchKafkaTemplate() {
		return new KafkaTemplate<>(searchProducerFactory());
	}
}
