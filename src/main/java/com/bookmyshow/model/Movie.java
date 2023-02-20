package com.bookmyshow.model;

import com.bookmyshow.model.enums.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	@Temporal(TemporalType.TIME)
	private Date duration;

	@Column(nullable = false)
	private String language;

	@Column(nullable = false)
	private Genre genre;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "movies_actor",
	           joinColumns = @JoinColumn(name="movie_id"),
	           inverseJoinColumns = @JoinColumn(name = "actor_id"))
	private Set<Actor> actors = new HashSet<>();

	public Movie(String title, String description, Date duration, String language, Genre genre) {
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.language = language;
		this.genre = genre;
	}
}
