package com.bookmyshow.dto;

import com.bookmyshow.model.Theatre;
import lombok.Getter;

@Getter
public class TheatreResponseDTO {
	private final int id;
	private final String name;
	private final String address;
	private final String city;
	private final String state;

	public TheatreResponseDTO(Theatre theatre) {
		this.id = theatre.getId();
		this.name = theatre.getName();
		this.address = theatre.getAddress();
		this.city = theatre.getCity().getName();
		this.state = theatre.getCity().getState();
	}
}
