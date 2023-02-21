package com.bookmyshow.model;

import com.bookmyshow.model.enums.SeatType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "audi_seats")
public class AudiSeat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String seatNo;

	@Column(nullable = false)
	private SeatType seatType;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "audi_id", nullable = false)
	private Audi audi;

	public AudiSeat(String seatNo, SeatType seatType, Audi audi) {
		this.seatNo = seatNo;
		this.seatType = seatType;
		this.audi = audi;
	}
}
