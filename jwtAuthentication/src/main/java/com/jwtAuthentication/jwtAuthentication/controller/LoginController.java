package com.jwtAuthentication.jwtAuthentication.controller;

import com.jwtAuthentication.jwtAuthentication.entity.LoginModel;
import com.jwtAuthentication.jwtAuthentication.entity.RefreshRequest;
import com.jwtAuthentication.jwtAuthentication.entity.RefreshToken;
import com.jwtAuthentication.jwtAuthentication.security.JwtUtility;
import com.jwtAuthentication.jwtAuthentication.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
    @RequestMapping("/auth")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginModel loginModel) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtility.generateToken(loginModel.getUsername());
        String refreshToken = refreshTokenService.createRefreshToken(loginModel.getUsername());
        return ResponseEntity.ok(new LoginResponse("login successfully",token,refreshToken));
    }


    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse>  refresh(@RequestBody RefreshRequest request) throws Exception {
        RefreshToken token2 = refreshTokenService.findByToken(request.getToken()).get();
        String jwtToken =null;
        if(refreshTokenService.verifyToken(token2.getToken())){
            String username = token2.getUsername();
            jwtToken = jwtUtility.generateToken(username);
        }
        return new ResponseEntity<>(new LoginResponse("Login successfully",jwtToken,request.getToken()),HttpStatus.OK);
    }

    public record LoginResponse(String msg,String token,String refreshToken){}
}
