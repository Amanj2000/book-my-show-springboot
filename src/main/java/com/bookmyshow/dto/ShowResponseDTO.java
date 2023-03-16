package com.bookmyshow.dto;

import com.bookmyshow.model.Show;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ShowResponseDTO {
	private final int showId;
	private final Date date;
	private final Date startTime;
	private final Date endTime;
	private final String movieName;
	private final AudiResponseDTO audi;
	private final List<ShowSeatDTO> showSeats;

	public ShowResponseDTO(Show show) {
		this.showId = show.getId();
		this.date = show.getDate();
		this.startTime = show.getStartTime();
		this.endTime = show.getEndTime();
		this.movieName = show.getMovie().getTitle();
		this.audi = new AudiResponseDTO(show.getAudi(), show.getShowSeats().size());
		this.showSeats = show.getShowSeats()
		                     .stream()
		                     .map(ShowSeatDTO::new)
		                     .collect(Collectors.toList());
	}
}
