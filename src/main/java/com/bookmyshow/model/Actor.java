package com.bookmyshow.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "actors")
public class Actor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true)
	private String name;

	@ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Set<Movie> movies = new HashSet<>();

	public Actor(String name) {
		this.name = name;
	}
}
