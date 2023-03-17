package com.bookmyshow.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "shows")
public class Show {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "movie_id", nullable = false)
	private Movie movie;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "audi_id", nullable = false)
	private Audi audi;

	@OneToMany(mappedBy = "show", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<ShowSeat> showSeats = new ArrayList<>();

	public Show(Date date, Date startTime, Date endTime, Movie movie, Audi audi) {
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.movie = movie;
		this.audi = audi;
	}

	@Override
	public String toString() {
		return String.format("{show_id: %d, startTime: %s, endTime: %s, movie: %s}", id, startTime, endTime, movie.getTitle());
	}
}
