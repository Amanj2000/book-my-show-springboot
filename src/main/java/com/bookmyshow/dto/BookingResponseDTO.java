package com.bookmyshow.dto;

import com.bookmyshow.model.Booking;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BookingResponseDTO {
	private final int id;
	private final int noOfSeats;
	private final int totalPrice;
	private final Date bookingTime;
	private final String movieName;
	private final int audiNo;
	private final String theatre;
	private final String city;
	private final Date date;
	private final Date startTime;
	private final Date endTime;
	private final List<String> seatNos;

	public BookingResponseDTO(Booking booking) {
		this.id = booking.getId();
		this.noOfSeats = booking.getNoOfSeats();
		this.totalPrice = booking.getTotalPrice();
		this.bookingTime = booking.getBookingTime();
		this.movieName = booking.getShow().getMovie().getTitle();
		this.audiNo = booking.getShow().getAudi().getAudiNo();
		this.theatre = booking.getShow().getAudi().getTheatre().getName();
		this.city = booking.getShow().getAudi().getTheatre().getCity().getName();
		this.date = booking.getShow().getDate();
		this.startTime = booking.getShow().getStartTime();
		this.endTime = booking.getShow().getEndTime();
		this.seatNos = booking.getShowSeats()
		                      .stream()
		                      .map(showSeat -> showSeat.getAudiSeat().getSeatNo())
		                      .collect(Collectors.toList());
	}
}
