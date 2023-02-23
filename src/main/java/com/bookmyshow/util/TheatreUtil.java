package com.bookmyshow.util;

import com.bookmyshow.dto.TheatreRequestDTO;
import com.bookmyshow.model.City;
import com.bookmyshow.model.Theatre;
import com.bookmyshow.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TheatreUtil {

	@Autowired
	CityRepository cityRepository;

	public void mapTheatreRequestToTheatre(TheatreRequestDTO theatreRequestDTO, Theatre theatre) {
		theatre.setName(theatreRequestDTO.getName());
		theatre.setAddress(theatreRequestDTO.getAddress());
		theatre.setCity(getCity(theatreRequestDTO.getCity(), theatreRequestDTO.getState()));
	}

	private City getCity(String city, String state) {
		Optional<City> cityOptional = cityRepository.findByNameAndState(city, state);
		return cityOptional.orElseGet(() -> cityRepository.save(new City(city, state)));
	}
}
