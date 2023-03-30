package com.bookmyshow.repository;

import com.bookmyshow.model.Token;
import com.bookmyshow.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, Integer> {
	Optional<Token> findByUser(User user);
	Optional<Token> findByToken(String token);
}
