package com.bookmyshow.service;

import com.bookmyshow.dto.AudiSeatRequestDTO;
import com.bookmyshow.dto.AudiSeatResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Audi;
import com.bookmyshow.model.AudiSeat;
import com.bookmyshow.model.Theatre;
import com.bookmyshow.model.enums.SeatType;
import com.bookmyshow.repository.AudiRepository;
import com.bookmyshow.repository.AudiSeatRepository;
import com.bookmyshow.repository.TheatreRepository;
import com.bookmyshow.util.AudiSeatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AudiSeatService {

	@Autowired
	TheatreRepository theatreRepository;

	@Autowired
	AudiRepository audiRepository;

	@Autowired
	AudiSeatRepository audiSeatRepository;

	@Autowired
	AudiSeatUtil audiSeatUtil;

	public List<AudiSeatResponseDTO> getAllAudiSeats(int theatreId, int audiNo) {
		Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
		if(theatreOptional.isPresent()) {
			Theatre theatre = theatreOptional.get();
			Optional<Audi> audiOptional = audiRepository.findByAudiNoAndTheatre(audiNo, theatre);
			if(audiOptional.isPresent()) {
				Audi audi = audiOptional.get();
				return audiSeatRepository.findByAudi(audi).stream()
				                         .map(AudiSeatResponseDTO::new)
				                         .collect(Collectors.toList());
			}
			return Collections.emptyList();
		}
		return Collections.emptyList();
	}

	public AudiSeatResponseDTO getAudiSeat(int theatreId, int audiNo, String seatNo) {
		Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
		if(theatreOptional.isPresent()) {
			Theatre theatre = theatreOptional.get();
			Optional<Audi> audiOptional = audiRepository.findByAudiNoAndTheatre(audiNo, theatre);
			if(audiOptional.isPresent()) {
				Audi audi = audiOptional.get();
				return audiSeatRepository.findByAudiAndSeatNo(audi, seatNo)
				                         .map(AudiSeatResponseDTO::new)
				                         .orElse(null);
			}
			return null;
		}
		return null;
	}

	public ResponseDTO addAudiSeat(int theatreId, int audiNo, AudiSeatRequestDTO audiSeatRequestDTO) {
		Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
		if(theatreOptional.isPresent()) {
			Theatre theatre = theatreOptional.get();
			Optional<Audi> audiOptional = audiRepository.findByAudiNoAndTheatre(audiNo, theatre);
			if(audiOptional.isPresent()) {
				Audi audi = audiOptional.get();
				if(audiSeatUtil.isSeatTypeValid(audiSeatRequestDTO.getSeatType())) {
					if(audiSeatRepository.findByAudiAndSeatNo(audi, audiSeatRequestDTO.getSeatNo()).isPresent()) {
						return new ResponseDTO(false, String.format("audi with seat no. %s already present",
								audiSeatRequestDTO.getSeatNo()));
					}
					AudiSeat audiSeat = new AudiSeat();
					audiSeat.setSeatNo(audiSeatRequestDTO.getSeatNo());
					audiSeat.setSeatType(SeatType.valueOf(audiSeatRequestDTO.getSeatType()
					                                                        .toUpperCase()));
					audiSeat.setAudi(audiOptional.get());
					audiSeatRepository.save(audiSeat);
					return new ResponseDTO(true, String.format("audi seat %s saved successfully", audiSeatRequestDTO.getSeatNo()));
				}
				return new ResponseDTO(false, String.format("invalid seat type, select seat type from %s",
						Arrays.toString(SeatType.class.getEnumConstants())));
			}
			return new ResponseDTO(false, "invalid audi no");
		}
		return new ResponseDTO(false, "invalid theatre id");
	}

	public ResponseDTO updateAudiSeat(int theatreId, int audiNo, String seatNo, AudiSeatRequestDTO audiSeatRequestDTO) {
		Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
		if(theatreOptional.isPresent()) {
			Theatre theatre = theatreOptional.get();
			Optional<Audi> audiOptional = audiRepository.findByAudiNoAndTheatre(audiNo, theatre);
			if(audiOptional.isPresent()) {
				Optional<AudiSeat> audiSeatOptional = audiSeatRepository.findByAudiAndSeatNo(audiOptional.get(), seatNo);
				if(audiSeatOptional.isPresent()) {
					if (audiSeatUtil.isSeatTypeValid(audiSeatRequestDTO.getSeatType())) {
						AudiSeat audiSeat = audiSeatOptional.get();
						audiSeatOptional = audiSeatRepository.findByAudiAndSeatNo(audiOptional.get(), audiSeatRequestDTO.getSeatNo());
						if(audiSeatOptional.isPresent() && !audiSeatOptional.get().getId().equals(audiSeat.getId())) {
							return new ResponseDTO(false, String.format("audi with seat no. %s already present",
									audiSeatRequestDTO.getSeatNo()));
						}

						audiSeat.setSeatNo(audiSeatRequestDTO.getSeatNo());
						audiSeat.setSeatType(SeatType.valueOf(audiSeatRequestDTO.getSeatType()
						                                                        .toUpperCase()));
						audiSeatRepository.save(audiSeat);
						return new ResponseDTO(true, String.format("audi seat %s updated successfully", seatNo));
					}
					return new ResponseDTO(false, String.format("invalid seat type, select seat type from %s", Arrays.toString(SeatType.class.getEnumConstants())));
				}
				return new ResponseDTO(false, "invalid seat no");
			}
			return new ResponseDTO(false, "invalid audi no");
		}
		return new ResponseDTO(false, "invalid theatre id");
	}

	public ResponseDTO deleteAudiSeat(int theatreId, int audiNo, String seatNo) {
		Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
		if(theatreOptional.isPresent()) {
			Theatre theatre = theatreOptional.get();
			Optional<Audi> audiOptional = audiRepository.findByAudiNoAndTheatre(audiNo, theatre);
			if(audiOptional.isPresent()) {
				Audi audi = audiOptional.get();
				Optional<AudiSeat> audiSeatOptional = audiSeatRepository.findByAudiAndSeatNo(audi, seatNo);
				if(audiSeatOptional.isPresent()) {
					audiSeatRepository.delete(audiSeatOptional.get());
					return new ResponseDTO(true, String.format("audi seat %s deleted successfully", seatNo));
				}
				return new ResponseDTO(false, "invalid seat no");
			}
			return new ResponseDTO(false, "invalid audi no");
		}
		return new ResponseDTO(false, "invalid theatre id");
	}
}
