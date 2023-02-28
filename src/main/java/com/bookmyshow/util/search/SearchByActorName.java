package com.bookmyshow.util.search;

import com.bookmyshow.model.Movie;
import com.bookmyshow.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Component
public class SearchByActorName implements ISearchMovie {

	@Autowired
	ActorRepository actorRepository;

	@Override
	public List<Movie> search(String actorName) {
		Set<Movie> movies = new HashSet<>();
		actorRepository.findByNameContaining(actorName)
		               .forEach(actor -> movies.addAll(actor.getMovies()));
		return new ArrayList<>(movies);
	}
}
