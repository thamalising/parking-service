package com.tmz.parkingservice.dao;

import com.tmz.parkingservice.data.Token;
import com.tmz.parkingservice.data.Warden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Integer> {
    Optional<Token> findByWardenId(int id);
    Optional<Token> findByValue(String val);
}
