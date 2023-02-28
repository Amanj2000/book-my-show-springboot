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

	public void checkMovie(int movieId) {
		movieHelper.checkMovie(movieId);
	}

	public Movie getMovie(int movieId) {
		return movieHelper.getMovie(movieId);
	}

	public void checkAudi(int theatreId, int audiNo) {
		audiHelper.checkAudi(theatreId, audiNo);
	}

	public Audi getAudi(int theatreId, int audiNo) {
		return audiHelper.getAudi(theatreId, audiNo);
	}

	public void checkShow(int theatreId, int audiNo, int showId) {
		checkAudi(theatreId, audiNo);

		Audi audi = getAudi(theatreId, audiNo);
		if(!showRepository.existsByIdAndAudi(showId, audi))
			throw new EntityNotFoundException("invalid show id");
	}

	public void checkShow(int movieId, int showId) {
		checkMovie(movieId);

		Movie movie = getMovie(movieId);
		if(!showRepository.existsByIdAndMovie(showId, movie))
			throw new EntityNotFoundException("invalid show id");
	}

	public Show getShow(int showId) {
		return showRepository.findById(showId).get();
	}

	public void checkTime(Date startTime, Date endTime) {
		if(startTime.after(endTime))
			throw new IllegalArgumentException("start time should be before end time");
	}

	public void canAdd(int theatreId, int audiNo, Date date, Date startTime, Date endTime, int movieId) {
		checkAudi(theatreId, audiNo);
		checkMovie(movieId);
		checkTime(startTime, endTime);
		//TODO check overlapping time
	}

	public void canUpdate(int theatreId, int audiNo, int showId, Date date, Date startTime, Date endTime, int movieId) {
		checkShow(theatreId, audiNo, showId);
		checkMovie(movieId);
		checkTime(startTime, endTime);
		//TODO check overlapping time
	}

	public void canDelete(int theatreId, int audiNo, int showId) {
		checkShow(theatreId, audiNo, showId);
	}
}
