package com.bookmyshow.repository;

import com.bookmyshow.model.Actor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends CrudRepository<Actor, Integer> {
	boolean existsByName(String name);
	Optional<Actor> findByName(String name);
}
