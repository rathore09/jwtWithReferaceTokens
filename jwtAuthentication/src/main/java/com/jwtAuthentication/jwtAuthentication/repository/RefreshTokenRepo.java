package com.jwtAuthentication.jwtAuthentication.repository;

import com.jwtAuthentication.jwtAuthentication.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String username);
    void deleteByToken(String token);


}
