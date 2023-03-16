package com.bookmyshow;

import com.bookmyshow.dto.MovieRequestDTO;
import com.bookmyshow.model.*;
import com.bookmyshow.model.enums.Genre;
import com.bookmyshow.model.enums.SeatType;
import com.bookmyshow.repository.AudiRepository;
import com.bookmyshow.repository.AudiSeatRepository;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.repository.UserRepository;
import com.bookmyshow.service.MovieService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BookMyShowApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder,
	                                    MovieService movieService, AudiRepository audiRepository,
	                                    AudiSeatRepository audiSeatRepository) {
		return args -> {
//			saveUsers(userRepository, passwordEncoder);
//			saveMovies(movieService);
//			saveAudiSeats(audiRepository, audiSeatRepository);
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
	private void saveMovies(MovieService movieService) {
		MovieRequestDTO pathan = new MovieRequestDTO(
				"Pathan",
				"Pathaan is a 2023 Indian Hindi-language action thriller film directed by Siddharth Anand and produced by Aditya Chopra of Yash Raj Films.",
				146,
				"Hindi",
				"ACTION",
				Arrays.asList("Shah Rukh Khan", "Salman Khan", "John Abraham", "Deepika Padukone")
		);
		movieService.addMovie(pathan);

		MovieRequestDTO welcome = new MovieRequestDTO(
				"Welcome",
				"Welcome is an Indian 2007 Hindi-language comedy film written and directed by Anees Bazmee. ",
				159,
				"Hindi",
				"COMEDY",
				Arrays.asList("Akshay Kumar", "Nana Patekar", "Anil Kapoor", "Katrina Kaif")
		);
		movieService.addMovie(welcome);

		MovieRequestDTO ekThaTiger = new MovieRequestDTO(
				"Ek Tha Tiger",
				"Ek Tha Tiger is a 2012 Indian Hindi-language spy action-thriller film directed by Kabir Khan and written by Kabir and Neelesh Misra",
				132,
				"Hindi",
				"ACTION",
				Arrays.asList("Salman Khan", "Katrina Kaif")
		);
		movieService.addMovie(ekThaTiger);

		MovieRequestDTO bareillyKiBarfi = new MovieRequestDTO(
				"Bareilly Ki Barfi",
				"Bareilly Ki Barfi is a 2017 Indian Hindi-language romantic comedy film directed by Ashwiny Iyer Tiwari",
				122,
				"Hindi",
				"COMEDY",
				Arrays.asList("Rajkumar Rao", "Ayushmann Khurrana", "Kriti Sanon")
		);
		movieService.addMovie(bareillyKiBarfi);
	}

	@SuppressWarnings("SpellCheckingInspection")
	private void saveAudiSeats(AudiRepository audiRepository, AudiSeatRepository audiSeatRepository) {
		City bangalore = new City("Bangalore", "Karnataka");
		City agra = new City("Agra", "Uttar Pradesh");

		Theatre pvrPhoenix = new Theatre("PVR Phoenix MarketCity Mall", "Whitefield Road", bangalore);
		Theatre cinepolisNexus = new Theatre("Cinepolis Nexus Shantiniketan", "Thigalarapalya", bangalore);
		Theatre sanjayTalkies = new Theatre("Sanjay Talkies", "Sanjay Palace", agra);
		Theatre sarvmultiplex = new Theatre("Sarv Multiplex", "Sanjay Palace", agra);

		Audi audiPvr1 = new Audi(1, pvrPhoenix);
		Audi audiPvr2 = new Audi(2, pvrPhoenix);
		Audi audiCinepolis1 = new Audi(1, cinepolisNexus);
		Audi audiCinepolis2 = new Audi(2, cinepolisNexus);
		Audi audiSanjay1 = new Audi(1, sanjayTalkies);
		Audi audiSanjay2 = new Audi(2, sanjayTalkies);
		Audi audiSarv1 = new Audi(1, sarvmultiplex);
		Audi audiSarv2 = new Audi(2, sarvmultiplex);

		audiRepository.saveAll(Arrays.asList(audiPvr1, audiPvr2, audiCinepolis1, audiCinepolis2, audiSanjay1,
				audiSanjay2, audiSarv1, audiSarv2));

		audiSeatRepository.saveAll(getAudiSeats(audiPvr1));
		audiSeatRepository.saveAll(getAudiSeats(audiPvr2));
		audiSeatRepository.saveAll(getAudiSeats(audiCinepolis1));
		audiSeatRepository.saveAll(getAudiSeats(audiCinepolis2));
		audiSeatRepository.saveAll(getAudiSeats(audiSanjay1));
		audiSeatRepository.saveAll(getAudiSeats(audiSanjay2));
		audiSeatRepository.saveAll(getAudiSeats(audiSarv1));
		audiSeatRepository.saveAll(getAudiSeats(audiSarv2));
	}

	private List<AudiSeat> getAudiSeats(Audi audi) {
		List<AudiSeat> audiSeats = new ArrayList<>();
		audiSeats.add(new AudiSeat("A1", SeatType.SILVER, audi));
		audiSeats.add(new AudiSeat("A2", SeatType.SILVER, audi));
		audiSeats.add(new AudiSeat("B1", SeatType.GOLD, audi));
		audiSeats.add(new AudiSeat("B2", SeatType.GOLD, audi));
		audiSeats.add(new AudiSeat("C1", SeatType.RECLINER, audi));
		audiSeats.add(new AudiSeat("C2", SeatType.RECLINER, audi));
		return audiSeats;
	}
}

