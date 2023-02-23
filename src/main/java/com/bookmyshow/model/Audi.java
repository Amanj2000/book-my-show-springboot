package com.bookmyshow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "audi")
public class Audi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private Integer audiNo;

	@Column(nullable = false)
	private Integer noOfSeats;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "theatre_id", nullable = false)
	private Theatre theatre;

	@OneToMany(mappedBy = "audi", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Show> shows = new ArrayList<>();

	public Audi(Integer audiNo, Integer noOfSeats, Theatre theatre) {
		this.audiNo = audiNo;
		this.noOfSeats = noOfSeats;
		this.theatre = theatre;
	}
}
