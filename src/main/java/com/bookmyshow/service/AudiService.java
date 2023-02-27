package com.bookmyshow.service;

import com.bookmyshow.dto.AudiRequestDTO;
import com.bookmyshow.dto.AudiResponseDTO;
import com.bookmyshow.dto.ResponseDTO;
import com.bookmyshow.model.Audi;
import com.bookmyshow.model.Theatre;
import com.bookmyshow.repository.AudiRepository;
import com.bookmyshow.util.AudiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AudiService {

	@Autowired
	AudiRepository audiRepository;

	@Autowired
	AudiUtil audiUtil;

	public List<AudiResponseDTO> getAllAudis(int theatreId) {
		ResponseDTO responseDTO = audiUtil.checkTheatre(theatreId);
		if(!responseDTO.isSuccess()) return Collections.emptyList();

		Theatre theatre = audiUtil.getTheatre(theatreId);
		return audiRepository.findByTheatre(theatre)
		                     .stream()
		                     .map(audi -> new AudiResponseDTO(audi, audiUtil.getSeatNo(audi)))
		                     .collect(Collectors.toList());
	}

	public AudiResponseDTO getAudi(int theatreId, int audiNo) {
		ResponseDTO responseDTO = audiUtil.checkAudi(theatreId, audiNo);
		if(!responseDTO.isSuccess()) return null;

		Audi audi = audiUtil.getAudi(theatreId, audiNo);
		return new AudiResponseDTO(audi, audiUtil.getSeatNo(audi));
	}

	public ResponseDTO addAudi(int theatreId, AudiRequestDTO audiRequestDTO) {
		ResponseDTO responseDTO = audiUtil.canAdd(theatreId, audiRequestDTO.getAudiNo());
		if(!responseDTO.isSuccess()) return responseDTO;

		Theatre theatre = audiUtil.getTheatre(theatreId);
		Audi audi = new Audi();
		audi.setAudiNo(audiRequestDTO.getAudiNo());
		audi.setTheatre(theatre);
		audiRepository.save(audi);

		return new ResponseDTO(true, String.format("audi %d added successfully", audi.getAudiNo()));
	}

	public ResponseDTO updateAudi(int theatreId, int audiNo, AudiRequestDTO audiRequestDTO) {
		ResponseDTO responseDTO = audiUtil.canUpdate(theatreId, audiNo, audiRequestDTO.getAudiNo());
		if(!responseDTO.isSuccess()) return responseDTO;

		Audi audi = audiUtil.getAudi(theatreId, audiNo);
		audi.setAudiNo(audiRequestDTO.getAudiNo());
		audiRepository.save(audi);

		return new ResponseDTO(true, String.format("audi %d updated successfully", audiNo));
	}

	public ResponseDTO deleteAudi(int theatreId, int audiNo) {
		ResponseDTO responseDTO = audiUtil.canDelete(theatreId, audiNo);
		if(!responseDTO.isSuccess()) return responseDTO;

		Audi audi = audiUtil.getAudi(theatreId, audiNo);
		audiRepository.delete(audi);

		return  new ResponseDTO(true, String.format("audi %d deleted successfully", audiNo));
	}
}
