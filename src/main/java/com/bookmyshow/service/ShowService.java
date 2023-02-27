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
import com.bookmyshow.util.ShowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
	ShowUtil showUtil;

	public List<ShowResponseDTO> getAllShows(int theatreId, int audiNo) {
		ResponseDTO responseDTO = showUtil.checkAudi(theatreId, audiNo);
		if(!responseDTO.isSuccess()) return Collections.emptyList();

		Audi audi = showUtil.getAudi(theatreId, audiNo);
		return showRepository.findByAudi(audi)
		                     .stream()
		                     .map(ShowResponseDTO::new)
		                     .collect(Collectors.toList());
	}

	public ShowResponseDTO getShow(int theatreId, int audiNo, int showId) {
		ResponseDTO responseDTO = showUtil.checkShow(theatreId, audiNo, showId);
		if(!responseDTO.isSuccess()) return null;

		Show show = showUtil.getShow(showId);
		return new ShowResponseDTO(show);
	}

	public List<ShowResponseDTO> getAllShows(int movieId) {
		ResponseDTO responseDTO = showUtil.checkMovie(movieId);
		if(!responseDTO.isSuccess()) return Collections.emptyList();

		Movie movie = showUtil.getMovie(movieId);
		return showRepository.findByMovie(movie)
		                     .stream()
		                     .map(ShowResponseDTO::new)
		                     .collect(Collectors.toList());
	}

	public ShowResponseDTO getShow(int movieId, int showId) {
		ResponseDTO responseDTO = showUtil.checkShow(movieId, showId);
		if(!responseDTO.isSuccess()) return null;

		Show show = showUtil.getShow(showId);
		return new ShowResponseDTO(show);
	}

	public ResponseDTO addShow(int theatreId, int audiNo, ShowRequestDTO showRequestDTO) {
		ResponseDTO responseDTO = showUtil.canAdd(theatreId, audiNo, showRequestDTO.getDate(),
				showRequestDTO.getStartTime(), showRequestDTO.getEndTime(), showRequestDTO.getMovieId());
		if(!responseDTO.isSuccess()) return responseDTO;

		Audi audi = showUtil.getAudi(theatreId, audiNo);
		Movie movie = showUtil.getMovie(showRequestDTO.getMovieId());

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
		ResponseDTO responseDTO = showUtil.canUpdate(theatreId, audiNo, showId, showRequestDTO.getDate(),
				showRequestDTO.getStartTime(), showRequestDTO.getEndTime(), showRequestDTO.getMovieId());
		if(!responseDTO.isSuccess()) return responseDTO;

		Movie movie = showUtil.getMovie(showRequestDTO.getMovieId());

		Show show = showUtil.getShow(showId);
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
		ResponseDTO responseDTO = showUtil.canDelete(theatreId, audiNo, showId);
		if(!responseDTO.isSuccess()) return responseDTO;

		Show show = showUtil.getShow(showId);
		showSeatRepository.deleteAll(showSeatRepository.findByShow(show));
		showRepository.delete(show);

		return new ResponseDTO(true, String.format("show of movie %s deleted successfully",
				show.getMovie().getTitle()));
	}
}
