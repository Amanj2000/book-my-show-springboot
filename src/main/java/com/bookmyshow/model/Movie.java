package com.bookmyshow.model;

import com.bookmyshow.model.enums.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
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
	private Integer duration;

	@Column(nullable = false)
	private String language;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Genre genre;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(name = "movies_actor",
	           joinColumns = @JoinColumn(name="movie_id"),
	           inverseJoinColumns = @JoinColumn(name = "actor_id"))
	private Set<Actor> actors = new HashSet<>();

	public Movie(String title, String description, Integer duration, String language, Genre genre, Set<Actor> actors) {
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.language = language;
		this.genre = genre;
		this.actors = actors;
	}
}
