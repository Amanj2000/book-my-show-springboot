package com.bookmyshow.service;

import com.bookmyshow.dto.AudiRequestDTO;
import com.bookmyshow.dto.AudiResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Audi;
import com.bookmyshow.model.Theatre;
import com.bookmyshow.repository.AudiRepository;
import com.bookmyshow.helper.AudiHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AudiService {

	@Autowired
	AudiRepository audiRepository;

	@Autowired
	AudiHelper audiHelper;

	public List<AudiResponseDTO> getAllAudis(int theatreId) {
		audiHelper.checkTheatre(theatreId);

		Theatre theatre = audiHelper.getTheatre(theatreId);
		return audiRepository.findByTheatre(theatre)
		                     .stream()
		                     .map(audi -> new AudiResponseDTO(audi, audiHelper.getSeatNo(audi)))
		                     .collect(Collectors.toList());
	}

	public AudiResponseDTO getAudi(int theatreId, int audiNo) {
		audiHelper.checkAudi(theatreId, audiNo);
		Audi audi = audiHelper.getAudi(theatreId, audiNo);
		return new AudiResponseDTO(audi, audiHelper.getSeatNo(audi));
	}

	public ResponseDTO addAudi(int theatreId, AudiRequestDTO audiRequestDTO) {
		audiHelper.canAdd(theatreId, audiRequestDTO.getAudiNo());

		Theatre theatre = audiHelper.getTheatre(theatreId);
		Audi audi = new Audi();
		audi.setAudiNo(audiRequestDTO.getAudiNo());
		audi.setTheatre(theatre);
		audiRepository.save(audi);

		return new ResponseDTO(true, String.format("audi %d added successfully", audi.getAudiNo()));
	}

	public ResponseDTO updateAudi(int theatreId, int audiNo, AudiRequestDTO audiRequestDTO) {
		audiHelper.canUpdate(theatreId, audiNo, audiRequestDTO.getAudiNo());

		Audi audi = audiHelper.getAudi(theatreId, audiNo);
		audi.setAudiNo(audiRequestDTO.getAudiNo());
		audiRepository.save(audi);

		return new ResponseDTO(true, String.format("audi %d updated successfully", audiNo));
	}

	public ResponseDTO deleteAudi(int theatreId, int audiNo) {
		audiHelper.canDelete(theatreId, audiNo);

		Audi audi = audiHelper.getAudi(theatreId, audiNo);
		audiRepository.delete(audi);

		return  new ResponseDTO(true, String.format("audi %d deleted successfully", audiNo));
	}
}
