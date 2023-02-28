package com.bookmyshow.helper;

import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Audi;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.Show;
import com.bookmyshow.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ShowHelper {
	@Autowired
	ShowRepository showRepository;

	@Autowired
	MovieHelper movieHelper;

	@Autowired
	AudiHelper audiHelper;

	public ResponseDTO checkMovie(int movieId) {
		return movieHelper.checkMovie(movieId);
	}

	public Movie getMovie(int movieId) {
		return movieHelper.getMovie(movieId);
	}

	public ResponseDTO checkAudi(int theatreId, int audiNo) {
		return audiHelper.checkAudi(theatreId, audiNo);
	}

	public Audi getAudi(int theatreId, int audiNo) {
		return audiHelper.getAudi(theatreId, audiNo);
	}

	public ResponseDTO checkShow(int theatreId, int audiNo, int showId) {
		ResponseDTO responseDTO = checkAudi(theatreId, audiNo);
		if(!responseDTO.isSuccess()) return responseDTO;

		Audi audi = getAudi(theatreId, audiNo);
		if(showRepository.existsByIdAndAudi(showId, audi)) {
			return new ResponseDTO(true, "");
		}
		return new ResponseDTO(false, "invalid show id");
	}

	public ResponseDTO checkShow(int movieId, int showId) {
		ResponseDTO responseDTO = checkMovie(movieId);
		if(!responseDTO.isSuccess()) return responseDTO;

		Movie movie = getMovie(movieId);
		if(showRepository.existsByIdAndMovie(showId, movie)) {
			return new ResponseDTO(true, "");
		}
		return new ResponseDTO(false, "invalid show id");
	}

	public Show getShow(int showId) {
		return showRepository.findById(showId).get();
	}

	public ResponseDTO checkTime(Date startTime, Date endTime) {
		if(startTime.before(endTime)) return new ResponseDTO(true, "");
		return new ResponseDTO(false, "start time should be before end time");
	}

	public ResponseDTO canAdd(int theatreId, int audiNo, Date date, Date startTime, Date endTime, int movieId) {
		ResponseDTO responseDTO = checkAudi(theatreId, audiNo);
		if(!responseDTO.isSuccess()) return responseDTO;

		responseDTO = checkMovie(movieId);
		if(!responseDTO.isSuccess()) return responseDTO;

		responseDTO = checkTime(startTime, endTime);
		if(!responseDTO.isSuccess()) return responseDTO;

		//TODO check overlapping time
		return new ResponseDTO(true, "");
	}

	public ResponseDTO canUpdate(int theatreId, int audiNo, int showId, Date date, Date startTime, Date endTime, int movieId) {
		ResponseDTO responseDTO = checkShow(theatreId, audiNo, showId);
		if(!responseDTO.isSuccess()) return responseDTO;

		responseDTO = checkMovie(movieId);
		if(!responseDTO.isSuccess()) return responseDTO;

		responseDTO = checkTime(startTime, endTime);
		if(!responseDTO.isSuccess()) return responseDTO;

		//TODO check overlapping time
		return new ResponseDTO(true, "");
	}

	public ResponseDTO canDelete(int theatreId, int audiNo, int showId) {
		return checkShow(theatreId, audiNo, showId);
	}
}
