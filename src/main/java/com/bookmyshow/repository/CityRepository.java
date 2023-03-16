package com.bookmyshow.repository;

import com.bookmyshow.model.City;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CityRepository extends CrudRepository<City, Integer> {
	Optional<City> findByNameAndState(String city, String state);
}
