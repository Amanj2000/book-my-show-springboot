package com.bookmyshow.util;

import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Audi;
import com.bookmyshow.model.AudiSeat;
import com.bookmyshow.model.enums.SeatType;
import com.bookmyshow.repository.AudiSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class AudiSeatUtil {
	@Autowired
	AudiSeatRepository audiSeatRepository;

	@Autowired
	AudiUtil audiUtil;

	public ResponseDTO checkAudi(int theatreId, int audiNo) {
		return audiUtil.checkAudi(theatreId, audiNo);
	}

	public Audi getAudi(int theatreId, int audiNo) {
		return audiUtil.getAudi(theatreId, audiNo);
	}

	public ResponseDTO checkAudiSeat(int theatreId, int audiNo, String seatNo) {
		ResponseDTO responseDTO = checkAudi(theatreId, audiNo);
		if(!responseDTO.isSuccess()) return responseDTO;

		Audi audi = getAudi(theatreId, audiNo);
		if(audiSeatRepository.existsByAudiAndSeatNo(audi, seatNo)) {
			return new ResponseDTO(true, "");
		}
		return new ResponseDTO(false, "invalid seat no");
	}

	public AudiSeat getAudiSeat(int theatreId, int audiNo, String seatNo) {
		Audi audi = getAudi(theatreId, audiNo);
		return audiSeatRepository.findByAudiAndSeatNo(audi, seatNo).get();
	}

	public ResponseDTO checkSeatType(String seatType) {
		try {
			SeatType.valueOf(seatType.toUpperCase());
			return new ResponseDTO(true, "");
		} catch(IllegalArgumentException e) {
			return new ResponseDTO(false, String.format("invalid seat type, select seat type from %s",
					Arrays.toString(SeatType.class.getEnumConstants())));
		}
	}

	public ResponseDTO canAdd(int theatreId, int audiNo, String newSeatNo, String newSeatType) {
		ResponseDTO responseDTO = checkAudi(theatreId, audiNo);
		if(!responseDTO.isSuccess()) return responseDTO;

		responseDTO = checkSeatType(newSeatType);
		if(!responseDTO.isSuccess()) return responseDTO;

		Audi audi = getAudi(theatreId, audiNo);
		if(audiSeatRepository.existsByAudiAndSeatNo(audi, newSeatNo)) {
			return new ResponseDTO(false, String.format("audi with seat no. %s already present", newSeatNo));
		}
		return new ResponseDTO(true, "");
	}

	public ResponseDTO canUpdate(int theatreId, int audiNo, String seatNo, String newSeatNo, String newSeatType) {
		ResponseDTO responseDTO = checkAudiSeat(theatreId, audiNo, seatNo);
		if(!responseDTO.isSuccess()) return responseDTO;

		responseDTO = checkSeatType(newSeatType);
		if(!responseDTO.isSuccess()) return responseDTO;

		Audi audi = getAudi(theatreId, audiNo);
		AudiSeat audiSeat = getAudiSeat(theatreId, audiNo, seatNo);
		Optional<AudiSeat> audiSeatOptional = audiSeatRepository.findByAudiAndSeatNo(audi, newSeatNo);
		if(audiSeatOptional.isPresent() && !audiSeatOptional.get().getId().equals(audiSeat.getId())) {
			return new ResponseDTO(false, String.format("audi with seat no. %s already present", newSeatNo));
		}
		return new ResponseDTO(true, "");
	}

	public ResponseDTO canDelete(int theatreId, int audiNo, String seatNo) {
		return checkAudiSeat(theatreId, audiNo, seatNo);
	}
}
