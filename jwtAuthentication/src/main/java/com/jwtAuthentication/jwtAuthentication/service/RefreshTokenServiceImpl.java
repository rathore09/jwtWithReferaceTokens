package com.jwtAuthentication.jwtAuthentication.service;

import com.jwtAuthentication.jwtAuthentication.entity.RefreshToken;
import com.jwtAuthentication.jwtAuthentication.repository.RefreshTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

    @Autowired
    RefreshTokenRepo refreshTokenRepo;

    @Override
    public String createRefreshToken(String username) {

        RefreshToken token =new  RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setExpiry(Instant.now().plus(1, ChronoUnit.DAYS));
        token.setUsername(username);
        return refreshTokenRepo.save(token).getToken();
    }

    @Override
    public Boolean verifyToken(String token) throws Exception{
        RefreshToken refreshToken = refreshTokenRepo.findByToken(token).get();

        if(refreshToken.getExpiry().isBefore(Instant.now())){
            refreshTokenRepo.deleteByToken(token);
            throw new Exception("referace token has been expired");
        }
        return true;
    }
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }
}
