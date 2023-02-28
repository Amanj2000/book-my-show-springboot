package com.bookmyshow.helper;

import com.bookmyshow.model.Audi;
import com.bookmyshow.model.AudiSeat;
import com.bookmyshow.model.enums.SeatType;
import com.bookmyshow.repository.AudiSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AudiSeatHelper {
	@Autowired
	AudiSeatRepository audiSeatRepository;

	@Autowired
	AudiHelper audiHelper;

	public void checkAudi(int theatreId, int audiNo) {
		audiHelper.checkAudi(theatreId, audiNo);
	}

	public Audi getAudi(int theatreId, int audiNo) {
		return audiHelper.getAudi(theatreId, audiNo);
	}

	public void checkAudiSeat(int theatreId, int audiNo, String seatNo) {
		checkAudi(theatreId, audiNo);

		Audi audi = getAudi(theatreId, audiNo);
		if(!audiSeatRepository.existsByAudiAndSeatNo(audi, seatNo))
			throw new EntityNotFoundException("invalid seat no");
	}

	public AudiSeat getAudiSeat(int theatreId, int audiNo, String seatNo) {
		Audi audi = getAudi(theatreId, audiNo);
		return audiSeatRepository.findByAudiAndSeatNo(audi, seatNo).get();
	}

	public void checkSeatType(String seatType) {
		try {
			SeatType.valueOf(seatType.toUpperCase());
		} catch(IllegalArgumentException e) {
			throw new IllegalArgumentException(String.format("invalid seat type, select seat type from %s",
					Arrays.toString(SeatType.class.getEnumConstants())));
		}
	}

	public void canAdd(int theatreId, int audiNo, String newSeatNo, String newSeatType) {
		checkAudi(theatreId, audiNo);
		checkSeatType(newSeatType);

		Audi audi = getAudi(theatreId, audiNo);
		if(audiSeatRepository.existsByAudiAndSeatNo(audi, newSeatNo))
			throw new IllegalArgumentException(String.format("audi with seat no. %s already present", newSeatNo));
	}

	public void canUpdate(int theatreId, int audiNo, String seatNo, String newSeatNo, String newSeatType) {
		checkAudiSeat(theatreId, audiNo, seatNo);
		checkSeatType(newSeatType);

		Audi audi = getAudi(theatreId, audiNo);
		AudiSeat audiSeat = getAudiSeat(theatreId, audiNo, seatNo);
		Optional<AudiSeat> audiSeatOptional = audiSeatRepository.findByAudiAndSeatNo(audi, newSeatNo);
		if(audiSeatOptional.isPresent() && !audiSeatOptional.get().getId().equals(audiSeat.getId()))
			throw new IllegalArgumentException(String.format("audi with seat no. %s already present", newSeatNo));
	}

	public void canDelete(int theatreId, int audiNo, String seatNo) {
		checkAudiSeat(theatreId, audiNo, seatNo);
	}
}
