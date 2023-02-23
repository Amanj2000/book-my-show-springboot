package com.bookmyshow.service;

import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.dto.TheatreRequestDTO;
import com.bookmyshow.dto.TheatreResponseDTO;
import com.bookmyshow.model.Theatre;
import com.bookmyshow.repository.TheatreRepository;
import com.bookmyshow.util.TheatreUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TheatreService {

	@Autowired
	TheatreRepository theatreRepository;

	@Autowired
	TheatreUtil theatreUtil;

	public List<TheatreResponseDTO> getAllTheatres() {
		List<TheatreResponseDTO> theatres = new ArrayList<>();
		theatreRepository.findAll()
		                 .forEach(theatre -> theatres.add(new TheatreResponseDTO(theatre)));
		return theatres;
	}

	public TheatreResponseDTO getTheatre(int theatreId) {
		Optional<Theatre> theatre = theatreRepository.findById(theatreId);
		return theatre.map(TheatreResponseDTO::new)
		              .orElse(null);
	}

	public ResponseDTO addTheatre(TheatreRequestDTO theatreRequestDTO) {
		Theatre theatre = new Theatre();
		theatreUtil.mapTheatreRequestToTheatre(theatreRequestDTO, theatre);
		theatreRepository.save(theatre);
		return new ResponseDTO(true, String.format("theatre %s saved successfully", theatre.getName()));
	}

	public ResponseDTO updateTheatre(int theatreId, TheatreRequestDTO theatreRequestDTO) {
		Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
		if (theatreOptional.isPresent()) {
			Theatre theatre = theatreOptional.get();
			theatreUtil.mapTheatreRequestToTheatre(theatreRequestDTO, theatre);
			theatreRepository.save(theatre);
			return new ResponseDTO(true, String.format("theatre %s updated successfully", theatre.getName()));
		}
		return new ResponseDTO(false, "invalid theatre id");
	}

	public ResponseDTO deleteTheatre(int theatreId) {
		if(theatreRepository.existsById(theatreId)) {
			Theatre theatre = theatreRepository.findById(theatreId).get();
			theatreRepository.delete(theatre);
			return new ResponseDTO(true, String.format("theatre %s deleted successfully", theatre.getName()));
		}
		return new ResponseDTO(false, "invalid theatre id");
	}
}
