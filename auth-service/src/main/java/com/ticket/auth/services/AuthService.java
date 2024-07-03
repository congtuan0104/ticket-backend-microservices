package com.ticket.auth.services;

import com.ticket.auth.entities.*;
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

    public RegisterResponse register(RegisterRequest registerRequest) {
        //do validation if user already exists
        UserVO user = restTemplate.getForObject("http://user-service/api/users?email=" + registerRequest.getEmail(), UserVO.class);
        Assert.isNull(user, "Tài khoản đã tồn tại");

        registerRequest.setPassword(BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt()));
//
        UserVO userVO = restTemplate.postForObject("http://user-service/api/users", registerRequest, UserVO.class);
        Assert.notNull(userVO, "Tạo tài khoản thất bại");

        String accessToken = jwt.generate(registerRequest.getEmail(), "ACCESS");
        String refreshToken = jwt.generate(registerRequest.getEmail(), "REFRESH");

        return new RegisterResponse(accessToken, refreshToken, userVO);

    }

    public LoginResponse login(LoginRequest loginRequest) {
        // kiểm tra user có tồn tại không
        UserVO userVO = restTemplate.getForObject("http://user-service/api/users?email=" + loginRequest.getEmail(), UserVO.class);
        Assert.notNull(userVO, "Tài khoản không tồn tại");

//        // kiểm tra password có đúng không
        if (!BCrypt.checkpw(loginRequest.getPassword(), userVO.getPassword())) {
            throw new RuntimeException("Mật khẩu không chính xác");
        }

        String accessToken = jwt.generate(loginRequest.getEmail(), "ACCESS");
        String refreshToken = jwt.generate(loginRequest.getEmail(), "REFRESH");

        return new LoginResponse(accessToken, refreshToken, userVO);
    }

}
