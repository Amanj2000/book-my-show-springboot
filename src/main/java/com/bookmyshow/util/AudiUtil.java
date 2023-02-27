package com.bookmyshow.util;

import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Audi;
import com.bookmyshow.model.Theatre;
import com.bookmyshow.repository.AudiRepository;
import com.bookmyshow.repository.AudiSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AudiUtil {

	@Autowired
	AudiRepository audiRepository;

	@Autowired
	AudiSeatRepository audiSeatRepository;

	@Autowired
	TheatreUtil theatreUtil;

	public ResponseDTO checkTheatre(int theatreId) {
		return theatreUtil.checkTheatre(theatreId);
	}

	public Theatre getTheatre(int theatreId) {
		return theatreUtil.getTheatre(theatreId);
	}

	public ResponseDTO checkAudi(int theatreId, int audiNo) {
		ResponseDTO responseDTO = checkTheatre(theatreId);
		if(!responseDTO.isSuccess()) return responseDTO;

		Theatre theatre = getTheatre(theatreId);
		if(audiRepository.existsByAudiNoAndTheatre(audiNo, theatre)) {
			return new ResponseDTO(true, "");
		}
		return new ResponseDTO(false, "invalid audi no");
	}

	public Audi getAudi(int theatreId, int audiNo) {
		Theatre theatre = getTheatre(theatreId);
		return audiRepository.findByAudiNoAndTheatre(audiNo, theatre).get();
	}

	public int getSeatNo(Audi audi) {
		return audiSeatRepository.countByAudi(audi);
	}

	public ResponseDTO canAdd(int theatreId, int newAudiNo) {
		ResponseDTO responseDTO = checkTheatre(theatreId);
		if(!responseDTO.isSuccess()) return responseDTO;

		Theatre theatre = getTheatre(theatreId);
		if(audiRepository.existsByAudiNoAndTheatre(newAudiNo, theatre)) {
			return new ResponseDTO(false, String.format("audi with no. %d already exists.", newAudiNo));
		}
		return new ResponseDTO(true, "");
	}

	public ResponseDTO canUpdate(int theatreId, int audiNo, int newAudiNo) {
		ResponseDTO responseDTO = checkAudi(theatreId, audiNo);
		if(!responseDTO.isSuccess()) return responseDTO;

		Theatre theatre = getTheatre(theatreId);
		Audi audi = getAudi(theatreId, audiNo);
		Optional<Audi> audiOptional = audiRepository.findByAudiNoAndTheatre(newAudiNo, theatre);
		if(audiOptional.isPresent() && !audiOptional.get().getId().equals(audi.getId())) {
			return new ResponseDTO(false, String.format("audi with no. %d already exists.", newAudiNo));
		}
		return new ResponseDTO(true, "");
	}

	public ResponseDTO canDelete(int theatreId, int audiNo) {
		return checkAudi(theatreId, audiNo);
	}
}
