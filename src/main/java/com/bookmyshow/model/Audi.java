package com.bookmyshow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Constraint;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "audi", uniqueConstraints = {@UniqueConstraint(columnNames = {"audiNo", "theatre_id"})})
public class Audi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private Integer audiNo;

	@Column(nullable = false)
	private Integer noOfSeats = 0;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "theatre_id", nullable = false)
	private Theatre theatre;

	@OneToMany(mappedBy = "audi", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Show> shows = new ArrayList<>();

	public Audi(Integer audiNo, Theatre theatre) {
		this.audiNo = audiNo;
		this.theatre = theatre;
	}
}
