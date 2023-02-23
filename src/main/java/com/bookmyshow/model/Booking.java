package com.bookmyshow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private Integer noOfSeats;

	@Column(nullable = false)
	private Integer totalPrice;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookingTime;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "show_id", nullable = false)
	private Show show;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "booking", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	List<ShowSeat> showSeats = new ArrayList<>();

	public Booking(Integer noOfSeats, Integer totalPrice, Date bookingTime, Show show, User user) {
		this.noOfSeats = noOfSeats;
		this.totalPrice = totalPrice;
		this.bookingTime = bookingTime;
		this.show = show;
		this.user = user;
	}
}
