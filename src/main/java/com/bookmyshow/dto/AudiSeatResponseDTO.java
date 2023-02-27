package com.bookmyshow.dto;

import com.bookmyshow.model.AudiSeat;
import com.bookmyshow.model.enums.SeatType;
import lombok.Getter;

@Getter
public class AudiSeatResponseDTO {
	private final String seatNo;
	private final SeatType seatType;

	public AudiSeatResponseDTO(AudiSeat audiSeat) {
		this.seatNo = audiSeat.getSeatNo();
		this.seatType = audiSeat.getSeatType();
	}
}
