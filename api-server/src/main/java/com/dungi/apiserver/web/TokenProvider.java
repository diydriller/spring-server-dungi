package com.dungi.apiserver.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public void verifyToken(String jwtToken) {
        JWT.require(Algorithm.HMAC512(jwtSecret)).build().verify(jwtToken);
    }

    public String createToken(Long userId, String email)  {
        return JWT.create()
                .withClaim("id", userId)
                .withClaim("email", email)
                .sign(Algorithm.HMAC512(jwtSecret));
    }
}
