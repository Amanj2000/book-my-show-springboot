package com.bookmyshow.service;

import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.dto.ShowRequestDTO;
import com.bookmyshow.dto.ShowResponseDTO;
import com.bookmyshow.model.Audi;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.Show;
import com.bookmyshow.repository.ShowRepository;
import com.bookmyshow.repository.ShowSeatRepository;
import com.bookmyshow.helper.ShowHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {
	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private ShowSeatRepository showSeatRepository;

	@Autowired
	private ShowHelper showHelper;

	public List<ShowResponseDTO> getAllShows(int theatreId, int audiNo) {
		Audi audi = showHelper.getAudi(theatreId, audiNo);
		return showHelper.toShowResponseDTOS(showRepository.findByAudi(audi));
	}

	public ShowResponseDTO getShow(int theatreId, int audiNo, int showId) {
		Show show = showHelper.getShow(theatreId, audiNo, showId);
		return new ShowResponseDTO(show);
	}

	public List<ShowResponseDTO> getAllShows(int movieId) {
		Movie movie = showHelper.getMovie(movieId);
		return showHelper.toShowResponseDTOS(showRepository.findByMovie(movie));
	}

	public ShowResponseDTO getShow(int movieId, int showId) {
		Show show = showHelper.getShow(movieId, showId);
		return new ShowResponseDTO(show);
	}

	public ResponseDTO addShow(int theatreId, int audiNo, ShowRequestDTO showRequestDTO) {
		showHelper.canAdd(theatreId, audiNo, showRequestDTO.getStartTime(), showRequestDTO.getEndTime());

		Audi audi = showHelper.getAudi(theatreId, audiNo);

		Show show = new Show();
		showHelper.mapShowRequestToShow(showRequestDTO, show);
		show.setAudi(audi);
		show.setShowSeats(showHelper.addShowSeats(audi, show, showRequestDTO.getPrice()));
		showRepository.save(show);
		return new ResponseDTO(String.format("show of movie %s added successfully", show.getMovie().getTitle()));
	}

	public ResponseDTO updateShow(int theatreId, int audiNo, int showId, ShowRequestDTO showRequestDTO) {
		showHelper.canUpdate(theatreId, audiNo, showRequestDTO.getStartTime(), showRequestDTO.getEndTime(), showId);

		Show show = showHelper.getShow(theatreId, audiNo, showId);
		showHelper.mapShowRequestToShow(showRequestDTO, show);
		showSeatRepository.findByShow(show).forEach(showSeat -> {
			showSeat.setPrice(showRequestDTO.getPrice());
			showSeatRepository.save(showSeat);
		});
		showRepository.save(show);
		return new ResponseDTO(String.format("show of movie %s updated successfully", show.getMovie().getTitle()));
	}

	public ResponseDTO deleteShow(int theatreId, int audiNo, int showId) {
		Show show = showHelper.getShow(theatreId, audiNo, showId);
		showSeatRepository.deleteAll(showSeatRepository.findByShow(show));
		showRepository.delete(show);

		return new ResponseDTO(String.format("show of movie %s deleted successfully", show.getMovie().getTitle()));
	}
}
