package com.bookmyshow.helper;

import com.bookmyshow.dto.ShowRequestDTO;
import com.bookmyshow.dto.ShowResponseDTO;
import com.bookmyshow.model.*;
import com.bookmyshow.repository.AudiSeatRepository;
import com.bookmyshow.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShowHelper {
	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private AudiSeatRepository audiSeatRepository;

	@Autowired
	private MovieHelper movieHelper;

	@Autowired
	private AudiHelper audiHelper;

	public List<ShowResponseDTO> toShowResponseDTOS(List<Show> shows) {
		return shows.stream()
		            .map(ShowResponseDTO::new)
		            .collect(Collectors.toList());
	}

	public void mapShowRequestToShow(ShowRequestDTO showRequestDTO, Show show) {
		Movie movie = getMovie(showRequestDTO.getMovieId());
		show.setDate(showRequestDTO.getDate());
		show.setStartTime(showRequestDTO.getStartTime());
		show.setEndTime(showRequestDTO.getEndTime());
		show.setMovie(movie);
	}

	public List<ShowSeat> addShowSeats(Audi audi, Show show, int price) {
		return audiSeatRepository.findByAudi(audi)
		                         .stream()
		                         .map(audiSeat -> new ShowSeat(price, show, audiSeat))
		                         .collect(Collectors.toList());
	}

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

	private boolean checkOverlap(Show show, Date startTime, Date endTime) {
		return (show.getStartTime().compareTo(endTime) < 0 && show.getEndTime().compareTo(startTime) > 0);
	}

	private void checkSlot(int theatreId, int audiNo, Date startTime, Date endTime, int excludeShowId) {
		Audi audi = getAudi(theatreId, audiNo);
		List<Show> overlappingShows = showRepository.findByAudi(audi)
		                                            .stream()
		                                            .filter(show -> show.getId() != excludeShowId &&
		                                                            checkOverlap(show, startTime, endTime))
		                                            .collect(Collectors.toList());
		if(!overlappingShows.isEmpty())
			throw new IllegalArgumentException(String.format("show timing overlapping with these shows %s", overlappingShows));
	}

	public void canAdd(int theatreId, int audiNo, Date startTime, Date endTime) {
		checkTime(startTime, endTime);
		checkSlot(theatreId, audiNo, startTime, endTime, 0);
	}

	public void canUpdate(int theatreId, int audiNo, Date startTime, Date endTime, int showId) {
		checkTime(startTime, endTime);
		checkSlot(theatreId, audiNo, startTime, endTime, showId);
	}
}
