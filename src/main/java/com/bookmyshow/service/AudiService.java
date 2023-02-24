package com.bookmyshow.service;

import com.bookmyshow.dto.AudiRequestDTO;
import com.bookmyshow.dto.AudiResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Audi;
import com.bookmyshow.model.Theatre;
import com.bookmyshow.repository.AudiRepository;
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

	public List<AudiResponseDTO> getAllAudis(int theatreId) {
		if(theatreRepository.existsById(theatreId)) {
			return audiRepository.findByTheatre(theatreRepository.findById(theatreId).get())
			                     .stream()
			                     .map(AudiResponseDTO::new)
			                     .collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	public AudiResponseDTO getAudi(int theatreId, int audiNo) {
		if(theatreRepository.existsById(theatreId)) {
			Theatre theatre = theatreRepository.findById(theatreId).get();
			Optional<Audi> audiOptional = audiRepository.findByAudiNoAndTheatre(audiNo, theatre);
			return audiOptional.map(AudiResponseDTO::new)
			                   .orElse(null);
		}
		return null;
	}

	public ResponseDTO addAudi(int theatreId, AudiRequestDTO audiRequestDTO) {
		if(theatreRepository.existsById(theatreId)) {
			Theatre theatre = theatreRepository.findById(theatreId).get();
			Optional<Audi> audiOptional = audiRepository.findByAudiNoAndTheatre(audiRequestDTO.getAudiNo(), theatre);
			if(audiOptional.isPresent()) {
				return new ResponseDTO(false, String.format("audi with no. %d already exists.",
						audiRequestDTO.getAudiNo()));
			}
			Audi audi = new Audi();
			audi.setAudiNo(audiRequestDTO.getAudiNo());
			audi.setTheatre(theatreRepository.findById(theatreId).get());
			audiRepository.save(audi);
			return new ResponseDTO(true, String.format("audi %d added successfully", audi.getAudiNo()));
		}
		return new ResponseDTO(false, "invalid theatre id");
	}

	public ResponseDTO updateAudi(int theatreId, int audiNo, AudiRequestDTO audiRequestDTO) {
		if(theatreRepository.existsById(theatreId)) {
			Theatre theatre = theatreRepository.findById(theatreId).get();
			Optional<Audi> audiOptional = audiRepository.findByAudiNoAndTheatre(audiNo, theatre);
			if(audiOptional.isPresent()) {
				Audi audi =  audiOptional.get();
				audiOptional = audiRepository.findByAudiNoAndTheatre(audiRequestDTO.getAudiNo(), theatre);
				if(audiOptional.isPresent()) {
					return new ResponseDTO(false, String.format("audi with no. %d already exists.",
							audiRequestDTO.getAudiNo()));
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
		if(theatreRepository.existsById(theatreId)) {
			Theatre theatre = theatreRepository.findById(theatreId).get();
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
