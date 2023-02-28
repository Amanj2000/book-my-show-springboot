package com.bookmyshow.service;

import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.dto.ShowRequestDTO;
import com.bookmyshow.dto.ShowResponseDTO;
import com.bookmyshow.model.Audi;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.Show;
import com.bookmyshow.model.ShowSeat;
import com.bookmyshow.repository.AudiSeatRepository;
import com.bookmyshow.repository.ShowRepository;
import com.bookmyshow.repository.ShowSeatRepository;
import com.bookmyshow.helper.ShowHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowService {
	@Autowired
	ShowRepository showRepository;

	@Autowired
	ShowSeatRepository showSeatRepository;

	@Autowired
	AudiSeatRepository audiSeatRepository;

	@Autowired
	ShowHelper showHelper;

	public List<ShowResponseDTO> getAllShows(int theatreId, int audiNo) {
		Audi audi = showHelper.getAudi(theatreId, audiNo);
		return showRepository.findByAudi(audi)
		                     .stream()
		                     .map(ShowResponseDTO::new)
		                     .collect(Collectors.toList());
	}

	public ShowResponseDTO getShow(int theatreId, int audiNo, int showId) {
		Show show = showHelper.getShow(theatreId, audiNo, showId);
		return new ShowResponseDTO(show);
	}

	public List<ShowResponseDTO> getAllShows(int movieId) {
		Movie movie = showHelper.getMovie(movieId);
		return showRepository.findByMovie(movie)
		                     .stream()
		                     .map(ShowResponseDTO::new)
		                     .collect(Collectors.toList());
	}

	public ShowResponseDTO getShow(int movieId, int showId) {
		Show show = showHelper.getShow(movieId, showId);
		return new ShowResponseDTO(show);
	}

	public ResponseDTO addShow(int theatreId, int audiNo, ShowRequestDTO showRequestDTO) {
		showHelper.canAdd(showRequestDTO.getDate(), showRequestDTO.getStartTime(), showRequestDTO.getEndTime());

		Audi audi = showHelper.getAudi(theatreId, audiNo);
		Movie movie = showHelper.getMovie(showRequestDTO.getMovieId());

		Show show = new Show();
		show.setDate(showRequestDTO.getDate());
		show.setStartTime(showRequestDTO.getStartTime());
		show.setEndTime(showRequestDTO.getEndTime());
		show.setMovie(movie);
		show.setAudi(audi);
		show.setShowSeats(audiSeatRepository.findByAudi(audi)
		                                    .stream()
		                                    .map(audiSeat -> new ShowSeat(showRequestDTO.getPrice(), show, audiSeat))
		                                    .collect(Collectors.toList()));
		showRepository.save(show);
		return new ResponseDTO(true, String.format("show of movie %s added successfully", movie.getTitle()));
	}

	public ResponseDTO updateShow(int theatreId, int audiNo, int showId, ShowRequestDTO showRequestDTO) {
		showHelper.canUpdate(showRequestDTO.getDate(), showRequestDTO.getStartTime(), showRequestDTO.getEndTime());

		Movie movie = showHelper.getMovie(showRequestDTO.getMovieId());
		Show show = showHelper.getShow(theatreId, audiNo, showId);

		show.setDate(showRequestDTO.getDate());
		show.setStartTime(showRequestDTO.getStartTime());
		show.setEndTime(showRequestDTO.getEndTime());
		show.setMovie(movie);
		showSeatRepository.findByShow(show).forEach(showSeat -> {
			showSeat.setPrice(showRequestDTO.getPrice());
			showSeatRepository.save(showSeat);
		});
		showRepository.save(show);
		return new ResponseDTO(true, String.format("show of movie %s updated successfully", movie.getTitle()));
	}

	public ResponseDTO deleteShow(int theatreId, int audiNo, int showId) {
		Show show = showHelper.getShow(theatreId, audiNo, showId);
		showSeatRepository.deleteAll(showSeatRepository.findByShow(show));
		showRepository.delete(show);

		return new ResponseDTO(true, String.format("show of movie %s deleted successfully", show.getMovie().getTitle()));
	}
}
