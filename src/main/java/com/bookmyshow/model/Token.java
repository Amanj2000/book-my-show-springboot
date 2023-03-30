package com.bookmyshow.model;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true, nullable = false, updatable = false)
	private String token;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "user_id", unique = true, nullable = false, updatable = false)
	private User user;
}
