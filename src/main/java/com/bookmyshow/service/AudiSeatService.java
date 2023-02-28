package com.bookmyshow.service;

import com.bookmyshow.dto.AudiSeatRequestDTO;
import com.bookmyshow.dto.AudiSeatResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Audi;
import com.bookmyshow.model.AudiSeat;
import com.bookmyshow.model.enums.SeatType;
import com.bookmyshow.repository.AudiSeatRepository;
import com.bookmyshow.helper.AudiSeatHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AudiSeatService {

	@Autowired
	AudiSeatRepository audiSeatRepository;

	@Autowired
	AudiSeatHelper audiSeatHelper;

	public List<AudiSeatResponseDTO> getAllAudiSeats(int theatreId, int audiNo) {
		Audi audi = audiSeatHelper.getAudi(theatreId, audiNo);
		return audiSeatRepository.findByAudi(audi).stream()
		                         .map(AudiSeatResponseDTO::new)
		                         .collect(Collectors.toList());
	}

	public AudiSeatResponseDTO getAudiSeat(int theatreId, int audiNo, String seatNo) {
		AudiSeat audiSeat = audiSeatHelper.getAudiSeat(theatreId, audiNo, seatNo);
		return new AudiSeatResponseDTO(audiSeat);
	}

	public ResponseDTO addAudiSeat(int theatreId, int audiNo, AudiSeatRequestDTO audiSeatRequestDTO) {
		audiSeatHelper.canAdd(theatreId, audiNo, audiSeatRequestDTO.getSeatNo(), audiSeatRequestDTO.getSeatType());

		Audi audi = audiSeatHelper.getAudi(theatreId, audiNo);

		AudiSeat audiSeat = new AudiSeat();
		audiSeat.setSeatNo(audiSeatRequestDTO.getSeatNo());
		audiSeat.setSeatType(SeatType.valueOf(audiSeatRequestDTO.getSeatType().toUpperCase()));
		audiSeat.setAudi(audi);
		audiSeatRepository.save(audiSeat);
		return new ResponseDTO(true, String.format("audi seat %s saved successfully", audiSeatRequestDTO.getSeatNo()));
	}

	public ResponseDTO updateAudiSeat(int theatreId, int audiNo, String seatNo, AudiSeatRequestDTO audiSeatRequestDTO) {
		audiSeatHelper.canUpdate(theatreId, audiNo, seatNo, audiSeatRequestDTO.getSeatNo(), audiSeatRequestDTO.getSeatType());

		AudiSeat audiSeat = audiSeatHelper.getAudiSeat(theatreId, audiNo, seatNo);
		audiSeat.setSeatNo(audiSeatRequestDTO.getSeatNo());
		audiSeat.setSeatType(SeatType.valueOf(audiSeatRequestDTO.getSeatType().toUpperCase()));
		audiSeatRepository.save(audiSeat);
		return new ResponseDTO(true, String.format("audi seat %s updated successfully", seatNo));
	}

	public ResponseDTO deleteAudiSeat(int theatreId, int audiNo, String seatNo) {
		AudiSeat audiSeat = audiSeatHelper.getAudiSeat(theatreId, audiNo, seatNo);
		audiSeatRepository.delete(audiSeat);
		return new ResponseDTO(true, String.format("audi seat %s deleted successfully", seatNo));
	}
}
