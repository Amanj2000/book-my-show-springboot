package com.bookmyshow.model;

import com.bookmyshow.model.enums.SeatStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "show_seats")
public class ShowSeat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private Integer price;

	@Column(nullable = false)
	private SeatStatus seatStatus;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "show_id", nullable = false)
	private Show show;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "audi_seat_id", nullable = false)
	private AudiSeat audiSeat;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "booking_id", nullable = false)
	private Booking booking;

	public ShowSeat(Integer price, SeatStatus seatStatus, Show show, AudiSeat audiSeat, Booking booking) {
		this.price = price;
		this.seatStatus = seatStatus;
		this.show = show;
		this.audiSeat = audiSeat;
		this.booking = booking;
	}
}
