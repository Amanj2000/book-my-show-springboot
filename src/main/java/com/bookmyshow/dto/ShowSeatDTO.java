package com.bookmyshow.dto;

import com.bookmyshow.model.ShowSeat;
import com.bookmyshow.model.enums.SeatStatus;
import com.bookmyshow.model.enums.SeatType;
import lombok.Getter;

@Getter
public class ShowSeatDTO {
	private final String seatNo;
	private final SeatType seatType;
	private final SeatStatus seatStatus;
	private final int price;

	public ShowSeatDTO(ShowSeat showSeat) {
		this.seatNo = showSeat.getAudiSeat().getSeatNo();
		this.seatType = showSeat.getAudiSeat().getSeatType();
		this.seatStatus = showSeat.getSeatStatus();
		this.price = showSeat.getPrice();
	}
}
