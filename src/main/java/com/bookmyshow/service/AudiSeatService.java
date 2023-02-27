package com.bookmyshow.service;

import com.bookmyshow.dto.AudiSeatRequestDTO;
import com.bookmyshow.dto.AudiSeatResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Audi;
import com.bookmyshow.model.AudiSeat;
import com.bookmyshow.model.enums.SeatType;
import com.bookmyshow.repository.AudiSeatRepository;
import com.bookmyshow.util.AudiSeatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AudiSeatService {

	@Autowired
	AudiSeatRepository audiSeatRepository;

	@Autowired
	AudiSeatUtil audiSeatUtil;

	public List<AudiSeatResponseDTO> getAllAudiSeats(int theatreId, int audiNo) {
		ResponseDTO responseDTO = audiSeatUtil.checkAudi(theatreId, audiNo);
		if(!responseDTO.isSuccess()) return Collections.emptyList();

		Audi audi = audiSeatUtil.getAudi(theatreId, audiNo);
		return audiSeatRepository.findByAudi(audi).stream()
		                         .map(AudiSeatResponseDTO::new)
		                         .collect(Collectors.toList());
	}

	public AudiSeatResponseDTO getAudiSeat(int theatreId, int audiNo, String seatNo) {
		ResponseDTO responseDTO = audiSeatUtil.checkAudiSeat(theatreId, audiNo, seatNo);
		if(!responseDTO.isSuccess()) return null;

		AudiSeat audiSeat = audiSeatUtil.getAudiSeat(theatreId, audiNo, seatNo);
		return new AudiSeatResponseDTO(audiSeat);
	}

	public ResponseDTO addAudiSeat(int theatreId, int audiNo, AudiSeatRequestDTO audiSeatRequestDTO) {
		ResponseDTO responseDTO = audiSeatUtil.canAdd(theatreId, audiNo, audiSeatRequestDTO.getSeatNo(), audiSeatRequestDTO.getSeatType());
		if(!responseDTO.isSuccess()) return responseDTO;

		Audi audi = audiSeatUtil.getAudi(theatreId, audiNo);

		AudiSeat audiSeat = new AudiSeat();
		audiSeat.setSeatNo(audiSeatRequestDTO.getSeatNo());
		audiSeat.setSeatType(SeatType.valueOf(audiSeatRequestDTO.getSeatType().toUpperCase()));
		audiSeat.setAudi(audi);
		audiSeatRepository.save(audiSeat);
		return new ResponseDTO(true, String.format("audi seat %s saved successfully", audiSeatRequestDTO.getSeatNo()));
	}

	public ResponseDTO updateAudiSeat(int theatreId, int audiNo, String seatNo, AudiSeatRequestDTO audiSeatRequestDTO) {
		ResponseDTO responseDTO = audiSeatUtil.canUpdate(theatreId, audiNo, seatNo,
				audiSeatRequestDTO.getSeatNo(), audiSeatRequestDTO.getSeatType());
		if(!responseDTO.isSuccess()) return responseDTO;

		AudiSeat audiSeat = audiSeatUtil.getAudiSeat(theatreId, audiNo, seatNo);
		audiSeat.setSeatNo(audiSeatRequestDTO.getSeatNo());
		audiSeat.setSeatType(SeatType.valueOf(audiSeatRequestDTO.getSeatType().toUpperCase()));
		audiSeatRepository.save(audiSeat);
		return new ResponseDTO(true, String.format("audi seat %s updated successfully", seatNo));
	}

	public ResponseDTO deleteAudiSeat(int theatreId, int audiNo, String seatNo) {
		ResponseDTO responseDTO = audiSeatUtil.canDelete(theatreId, audiNo, seatNo);
		if(!responseDTO.isSuccess()) return responseDTO;

		AudiSeat audiSeat = audiSeatUtil.getAudiSeat(theatreId, audiNo, seatNo);
		audiSeatRepository.delete(audiSeat);
		return new ResponseDTO(true, String.format("audi seat %s deleted successfully", seatNo));
	}
}
