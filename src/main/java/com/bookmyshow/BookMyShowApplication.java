package com.bookmyshow;

import com.bookmyshow.model.Actor;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.User;
import com.bookmyshow.model.enums.Genre;
import com.bookmyshow.repository.ActorRepository;
import com.bookmyshow.repository.MovieRepository;
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
	                                    MovieRepository movieRepository, ActorRepository actorRepository) {
		return args -> {
			saveMovies(movieRepository, actorRepository);
			saveUsers(userRepository, passwordEncoder);
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
	private void saveMovies(MovieRepository movieRepository, ActorRepository actorRepository) {
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

		actorRepository.saveAll(
				Arrays.asList(shahRukhKhan, johnAbraham, salmanKhan, rajkumarRao, ayushmannKhurrana, akshayKumar,
						nanaPatekar, anilKapoor, deepikaPadukone, katrinaKaif, kritiSanon));

		Movie pathan = new Movie(
				"Pathan",
				"Pathaan is a 2023 Indian Hindi-language action thriller film directed by Siddharth Anand and produced by Aditya Chopra of Yash Raj Films.",
				146,
				"Hindi",
				Genre.ACTION,
				new HashSet<>(Arrays.asList(shahRukhKhan, salmanKhan, johnAbraham, deepikaPadukone))
		);

		Movie welcome = new Movie(
				"Welcome",
				"Welcome is an Indian 2007 Hindi-language comedy film written and directed by Anees Bazmee. ",
				159,
				"Hindi",
				Genre.COMEDY,
				new HashSet<>(Arrays.asList(akshayKumar, nanaPatekar, anilKapoor, katrinaKaif))
		);

		Movie ekThaTiger = new Movie(
				"Ek Tha Tiger",
				"Ek Tha Tiger is a 2012 Indian Hindi-language spy action-thriller film directed by Kabir Khan and written by Kabir and Neelesh Misra",
				132,
				"Hindi",
				Genre.ACTION,
				new HashSet<>(Arrays.asList(salmanKhan, katrinaKaif))
		);

		Movie bareillyKiBarfi = new Movie(
				"Bareilly Ki Barfi",
				"Bareilly Ki Barfi is a 2017 Indian Hindi-language romantic comedy film directed by Ashwiny Iyer Tiwari",
				122,
				"Hindi",
				Genre.COMEDY,
				new HashSet<>(Arrays.asList(rajkumarRao, ayushmannKhurrana, kritiSanon))
		);

		movieRepository.save(pathan);
		movieRepository.save(welcome);
		movieRepository.save(ekThaTiger);
		movieRepository.save(bareillyKiBarfi);
	}
}

