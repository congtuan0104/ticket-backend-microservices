package com.ticket.auth.services;

import com.ticket.auth.entities.AuthRequest;
import com.ticket.auth.entities.AuthResponse;
import com.ticket.auth.entities.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtService jwt;

    @Autowired
    public AuthService(RestTemplate restTemplate, final JwtService jwt) {
        this.restTemplate = restTemplate;
        this.jwt = jwt;
    }

    public AuthResponse register(AuthRequest authRequest) {
        //do validation if user already exists
        authRequest.setPassword(BCrypt.hashpw(authRequest.getPassword(), BCrypt.gensalt()));
//
        UserVO userVO = restTemplate.postForObject("http://user-service/users", authRequest, UserVO.class);
        Assert.notNull(userVO, "Failed to register user. Please try again later");

        String accessToken = jwt.generate(authRequest.getEmail(), "ACCESS");
        String refreshToken = jwt.generate(authRequest.getEmail(), "REFRESH");

        return new AuthResponse(accessToken, refreshToken);

    }
}
