package com.bookmyshow.helper;

import com.bookmyshow.model.Audi;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.Show;
import com.bookmyshow.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Component
public class ShowHelper {
	@Autowired
	ShowRepository showRepository;

	@Autowired
	MovieHelper movieHelper;

	@Autowired
	AudiHelper audiHelper;

	public Movie getMovie(int movieId) {
		return movieHelper.getMovie(movieId);
	}

	public Audi getAudi(int theatreId, int audiNo) {
		return audiHelper.getAudi(theatreId, audiNo);
	}

	public Show getShow(int theatreId, int audiNo, int showId) {
		Audi audi = getAudi(theatreId, audiNo);
		if(!showRepository.existsByIdAndAudi(showId, audi))
			throw new EntityNotFoundException("invalid show id");
		return showRepository.findById(showId).get();
	}

	public Show getShow(int movieId, int showId) {
		Movie movie = getMovie(movieId);
		if(!showRepository.existsByIdAndMovie(showId, movie))
			throw new EntityNotFoundException("invalid show id");
		return showRepository.findById(showId).get();
	}

	private void checkTime(Date startTime, Date endTime) {
		if(startTime.after(endTime))
			throw new IllegalArgumentException("start time should be before end time");
	}

	public void canAdd(Date date, Date startTime, Date endTime) {
		checkTime(startTime, endTime);
		//TODO check overlapping time
	}

	public void canUpdate(Date date, Date startTime, Date endTime) {
		checkTime(startTime, endTime);
		//TODO check overlapping time
	}
}
