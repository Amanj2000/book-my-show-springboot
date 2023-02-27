package com.bookmyshow.util;

import com.bookmyshow.model.enums.SeatType;
import org.springframework.stereotype.Component;

@Component
public class AudiSeatUtil {

	public boolean isSeatTypeValid(String seatType) {
		try {
			SeatType.valueOf(seatType.toUpperCase());
			return true;
		} catch(IllegalArgumentException e) {
			return false;
		}
	}
}
