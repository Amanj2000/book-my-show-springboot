package com.bookmyshow.config;

import com.bookmyshow.dto.MovieResponseDTO;
import com.bookmyshow.dto.deserializer.MovieDTODeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapServer;

	@Bean
	public ConsumerFactory<String, List<MovieResponseDTO>> searchResultConsumerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configs.put(ConsumerConfig.CLIENT_ID_CONFIG, "search-result-consumer");
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "search-result-consumer-group");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MovieDTODeserializer.class);
		return new DefaultKafkaConsumerFactory<>(configs);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, List<MovieResponseDTO>> searchListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, List<MovieResponseDTO>> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(searchResultConsumerFactory());
		return factory;
	}
}
