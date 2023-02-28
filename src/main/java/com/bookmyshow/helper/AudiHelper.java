package com.bookmyshow.helper;

import com.bookmyshow.model.Audi;
import com.bookmyshow.model.Theatre;
import com.bookmyshow.repository.AudiRepository;
import com.bookmyshow.repository.AudiSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
public class AudiHelper {

	@Autowired
	AudiRepository audiRepository;

	@Autowired
	AudiSeatRepository audiSeatRepository;

	@Autowired
	TheatreHelper theatreHelper;

	public Theatre getTheatre(int theatreId) {
		return theatreHelper.getTheatre(theatreId);
	}

	public Audi getAudi(int theatreId, int audiNo) {
		Theatre theatre = getTheatre(theatreId);
		if(!audiRepository.existsByAudiNoAndTheatre(audiNo, theatre))
			throw new EntityNotFoundException("invalid audi no");
		return audiRepository.findByAudiNoAndTheatre(audiNo, theatre).get();
	}

	public int getSeatNo(Audi audi) {
		return audiSeatRepository.countByAudi(audi);
	}

	public void canAdd(int theatreId, int newAudiNo) {
		Theatre theatre = getTheatre(theatreId);
		if(audiRepository.existsByAudiNoAndTheatre(newAudiNo, theatre))
			throw new IllegalArgumentException(String.format("audi with no. %d already exists.", newAudiNo));
	}

	public void canUpdate(int theatreId, int audiNo, int newAudiNo) {
		Theatre theatre = getTheatre(theatreId);
		Audi audi = getAudi(theatreId, audiNo);
		Optional<Audi> audiOptional = audiRepository.findByAudiNoAndTheatre(newAudiNo, theatre);
		if(audiOptional.isPresent() && !audiOptional.get().getId().equals(audi.getId()))
			throw new IllegalArgumentException(String.format("audi with no. %d already exists.", newAudiNo));
	}
}
