package com.bookmyshow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "theatres")
public class Theatre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String address;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "city_id", nullable = false)
	private City city;

	@OneToMany(mappedBy = "theatre", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Set<Audi> audis = new HashSet<>();

	public Theatre(String name, String address, City city) {
		this.name = name;
		this.address = address;
		this.city = city;
	}
}
