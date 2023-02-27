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
		ResponseDTO responseDTO = theatreUtil.checkTheatre(theatreId);
		if(!responseDTO.isSuccess()) return null;

		Theatre theatre = theatreUtil.getTheatre(theatreId);
		return new TheatreResponseDTO(theatre);
	}

	public ResponseDTO addTheatre(TheatreRequestDTO theatreRequestDTO) {
		Theatre theatre = new Theatre();
		theatreUtil.mapTheatreRequestToTheatre(theatreRequestDTO, theatre);
		theatreRepository.save(theatre);
		return new ResponseDTO(true, String.format("theatre %s saved successfully", theatre.getName()));
	}

	public ResponseDTO updateTheatre(int theatreId, TheatreRequestDTO theatreRequestDTO) {
		ResponseDTO responseDTO = theatreUtil.canUpdate(theatreId);
		if(!responseDTO.isSuccess()) return responseDTO;

		Theatre theatre = theatreUtil.getTheatre(theatreId);
		theatreUtil.mapTheatreRequestToTheatre(theatreRequestDTO, theatre);
		theatreRepository.save(theatre);

		return new ResponseDTO(true, String.format("theatre %s updated successfully", theatre.getName()));
	}

	public ResponseDTO deleteTheatre(int theatreId) {
		ResponseDTO responseDTO = theatreUtil.canDelete(theatreId);
		if(!responseDTO.isSuccess()) return responseDTO;

		Theatre theatre = theatreUtil.getTheatre(theatreId);
		theatreRepository.delete(theatre);

		return new ResponseDTO(true, String.format("theatre %s deleted successfully", theatre.getName()));
	}
}
