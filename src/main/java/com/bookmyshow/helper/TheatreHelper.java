package com.bookmyshow.helper;

import com.bookmyshow.dto.TheatreRequestDTO;
import com.bookmyshow.model.City;
import com.bookmyshow.model.Theatre;
import com.bookmyshow.repository.CityRepository;
import com.bookmyshow.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
public class TheatreHelper {

	@Autowired
	CityRepository cityRepository;

	@Autowired
	TheatreRepository theatreRepository;

	public void mapTheatreRequestToTheatre(TheatreRequestDTO theatreRequestDTO, Theatre theatre) {
		theatre.setName(theatreRequestDTO.getName());
		theatre.setAddress(theatreRequestDTO.getAddress());
		theatre.setCity(getCity(theatreRequestDTO.getCity(), theatreRequestDTO.getState()));
	}

	private City getCity(String city, String state) {
		Optional<City> cityOptional = cityRepository.findByNameAndState(city, state);
		return cityOptional.orElseGet(() -> cityRepository.save(new City(city, state)));
	}

	public void checkTheatre(int theatreId) {
		if(!theatreRepository.existsById(theatreId))
			throw new EntityNotFoundException("invalid theatre id");
	}

	public Theatre getTheatre(int theatreId) {
		return theatreRepository.findById(theatreId).get();
	}

	public void canUpdate(int theatreId) {
		checkTheatre(theatreId);
	}

	public void canDelete(int theatreId) {
		checkTheatre(theatreId);
	}
}
