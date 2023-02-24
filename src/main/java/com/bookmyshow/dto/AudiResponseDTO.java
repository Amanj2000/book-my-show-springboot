package com.bookmyshow.dto;

import com.bookmyshow.model.Audi;
import lombok.Getter;

@Getter
public class AudiResponseDTO {
	private final int audiNo;
	private final int noOfSeats;
	private final String theatre;
	private final String city;

	public AudiResponseDTO(Audi audi) {
		this.audiNo = audi.getAudiNo();
		this.noOfSeats = audi.getNoOfSeats();
		this.theatre = audi.getTheatre().getName();
		this.city = audi.getTheatre().getCity().getName();
	}
}
