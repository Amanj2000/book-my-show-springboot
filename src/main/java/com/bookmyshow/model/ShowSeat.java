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
	private SeatStatus seatStatus = SeatStatus.Available;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "show_id", nullable = false)
	private Show show;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "audi_seat_id", nullable = false)
	private AudiSeat audiSeat;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "booked_seats",
	           joinColumns = @JoinColumn(name="show_seat_id"),
	           inverseJoinColumns = @JoinColumn(name = "booking_id"))
	private Booking booking;

	public ShowSeat(Integer price, Show show, AudiSeat audiSeat) {
		this.price = price;
		this.show = show;
		this.audiSeat = audiSeat;
	}
}
