package com.bookmyshow.dto;

import com.bookmyshow.model.Audi;
import lombok.Getter;

@Getter
public class AudiResponseDTO {
	private final int audiNo;
	private final int noOfSeats;
	private final String theatre;
	private final String city;

	public AudiResponseDTO(Audi audi, int noOfSeats) {
		this.audiNo = audi.getAudiNo();
		this.noOfSeats = noOfSeats;
		this.theatre = audi.getTheatre().getName();
		this.city = audi.getTheatre().getCity().getName();
	}
}
