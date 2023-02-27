package com.bookmyshow.service;

import com.bookmyshow.dto.AudiRequestDTO;
import com.bookmyshow.dto.AudiResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Audi;
import com.bookmyshow.model.Theatre;
import com.bookmyshow.repository.AudiRepository;
import com.bookmyshow.repository.AudiSeatRepository;
import com.bookmyshow.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AudiService {

	@Autowired
	AudiRepository audiRepository;

	@Autowired
	TheatreRepository theatreRepository;

	@Autowired
	AudiSeatRepository audiSeatRepository;

	public List<AudiResponseDTO> getAllAudis(int theatreId) {
		Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
		return theatreOptional.map(theatre -> audiRepository.findByTheatre(theatre)
		                                                    .stream()
		                                                    .map(audi -> new AudiResponseDTO(audi,
																		audiSeatRepository.countByAudi(audi)))
		                                                    .collect(Collectors.toList()))
		                      .orElse(Collections.emptyList());
	}

	public AudiResponseDTO getAudi(int theatreId, int audiNo) {
		Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
		return theatreOptional.flatMap(theatre -> audiRepository.findByAudiNoAndTheatre(audiNo, theatre)
		                                                        .map(audi -> new AudiResponseDTO(audi,
				                                                        audiSeatRepository.countByAudi(audi))))
		                      .orElse(null);
	}

	public ResponseDTO addAudi(int theatreId, AudiRequestDTO audiRequestDTO) {
		Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
		if(theatreOptional.isPresent()) {
			Theatre theatre = theatreOptional.get();
			Optional<Audi> audiOptional = audiRepository.findByAudiNoAndTheatre(audiRequestDTO.getAudiNo(), theatre);
			if(audiOptional.isPresent()) {
				return new ResponseDTO(false, String.format("audi with no. %d already exists.",
						audiRequestDTO.getAudiNo()));
			}
			Audi audi = new Audi();
			audi.setAudiNo(audiRequestDTO.getAudiNo());
			audi.setTheatre(theatre);
			audiRepository.save(audi);
			return new ResponseDTO(true, String.format("audi %d added successfully", audi.getAudiNo()));
		}
		return new ResponseDTO(false, "invalid theatre id");
	}

	public ResponseDTO updateAudi(int theatreId, int audiNo, AudiRequestDTO audiRequestDTO) {
		Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
		if(theatreOptional.isPresent()) {
			Theatre theatre = theatreOptional.get();
			Optional<Audi> audiOptional = audiRepository.findByAudiNoAndTheatre(audiNo, theatre);
			if(audiOptional.isPresent()) {
				Audi audi =  audiOptional.get();
				audiOptional = audiRepository.findByAudiNoAndTheatre(audiRequestDTO.getAudiNo(), theatre);
				if(audiOptional.isPresent() && !audiOptional.get().getId().equals(audi.getId())) {
					return new ResponseDTO(false, String.format("audi with no. %d already exists.", audiNo));
				}
				audi.setAudiNo(audiRequestDTO.getAudiNo());
				audiRepository.save(audi);
				return new ResponseDTO(true, String.format("audi %d updated successfully", audiNo));
			}
			return new ResponseDTO(false, "invalid audi no");
		}
		return new ResponseDTO(false, "invalid theatre id");
	}

	public ResponseDTO deleteAudi(int theatreId, int audiNo) {
		Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
		if(theatreOptional.isPresent()) {
			Theatre theatre = theatreOptional.get();
			Optional<Audi> audiOptional = audiRepository.findByAudiNoAndTheatre(audiNo, theatre);
			if(audiOptional.isPresent()) {
				audiRepository.delete(audiOptional.get());
				return new ResponseDTO(true, String.format("audi %d deleted successfully", audiNo));
			}
			return new ResponseDTO(false, "invalid audi no");
		}
		return new ResponseDTO(false, "invalid theatre id");
	}
}
