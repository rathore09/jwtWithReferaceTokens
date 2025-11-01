package com.jwtAuthentication.jwtAuthentication.service;

import com.jwtAuthentication.jwtAuthentication.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    public  String createRefreshToken(String username);

    public Boolean verifyToken(String token) throws  Exception;

    public Optional<RefreshToken> findByToken(String token) ;




}
