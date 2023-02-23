package com.bookmyshow;

import com.bookmyshow.model.*;
import com.bookmyshow.model.enums.Genre;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.repository.TheatreRepository;
import com.bookmyshow.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class BookMyShowApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder,
	                                    MovieRepository movieRepository,
	                                    TheatreRepository theatreRepository) {
		return args -> {
			saveUsers(userRepository, passwordEncoder);
			saveMovies(movieRepository);
			saveTheatres(theatreRepository);
		};
	}

	private void saveUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		User user1 = new User("user1@email.com",
				passwordEncoder.encode("password"),
				"user1", "one", 20);

		User user2 = new User("user2@email.com",
				passwordEncoder.encode("password"),
				"user2", "two", 23);

		userRepository.save(user1);
		userRepository.save(user2);
	}


	@SuppressWarnings("SpellCheckingInspection")
	private void saveMovies(MovieRepository movieRepository) {
		Actor shahRukhKhan = new Actor("Shah Rukh Khan");
		Actor johnAbraham = new Actor("John Abraham");
		Actor salmanKhan = new Actor("Salman Khan");
		Actor rajkumarRao = new Actor("Rajkumar Rao");
		Actor ayushmannKhurrana = new Actor("Ayushmann Khurrana");
		Actor akshayKumar = new Actor("Akshay Kumar");
		Actor nanaPatekar = new Actor("Nana Patekar");
		Actor anilKapoor = new Actor("Anil Kapoor");


		Actor deepikaPadukone = new Actor("Deepika Padukone");
		Actor katrinaKaif = new Actor("Katrina Kaif");
		Actor kritiSanon = new Actor("Kriti Sanon");

		Movie pathan = new Movie(
				"Pathan",
				"Pathaan is a 2023 Indian Hindi-language action thriller film directed by Siddharth Anand and produced by Aditya Chopra of Yash Raj Films.",
				146,
				"Hindi",
				Genre.ACTION,
				Arrays.asList(shahRukhKhan, salmanKhan, johnAbraham, deepikaPadukone)
		);

		Movie welcome = new Movie(
				"Welcome",
				"Welcome is an Indian 2007 Hindi-language comedy film written and directed by Anees Bazmee. ",
				159,
				"Hindi",
				Genre.COMEDY,
				Arrays.asList(akshayKumar, nanaPatekar, anilKapoor, katrinaKaif)
		);

		Movie ekThaTiger = new Movie(
				"Ek Tha Tiger",
				"Ek Tha Tiger is a 2012 Indian Hindi-language spy action-thriller film directed by Kabir Khan and written by Kabir and Neelesh Misra",
				132,
				"Hindi",
				Genre.ACTION,
				Arrays.asList(salmanKhan, katrinaKaif)
		);

		Movie bareillyKiBarfi = new Movie(
				"Bareilly Ki Barfi",
				"Bareilly Ki Barfi is a 2017 Indian Hindi-language romantic comedy film directed by Ashwiny Iyer Tiwari",
				122,
				"Hindi",
				Genre.COMEDY,
				Arrays.asList(rajkumarRao, ayushmannKhurrana, kritiSanon)
		);

		movieRepository.saveAll(Arrays.asList(pathan, welcome, ekThaTiger, bareillyKiBarfi));
	}

	@SuppressWarnings("SpellCheckingInspection")
	private void saveTheatres(TheatreRepository theatreRepository) {
		City bangalore = new City("Bangalore", "Karnataka");
		City agra = new City("Agra", "Uttar Pradesh");

		Theatre pvrPhoenix = new Theatre("PVR Phoenix MarketCity Mall", "Whitefield Road", bangalore);
		Theatre cinepolisNexus = new Theatre("Cinepolis Nexus Shantiniketan", "Thigalarapalya", bangalore);
		Theatre sanjayTalkies = new Theatre("Sanjay Talkies", "Sanjay Palace", agra);
		Theatre sarvmultiplex = new Theatre("Sarv Multiplex", "Sanjay Palace", agra);

		theatreRepository.saveAll(Arrays.asList(pvrPhoenix, cinepolisNexus, sanjayTalkies, sarvmultiplex));
	}
}

