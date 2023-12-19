package com.project.dungi.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.dungi.domain.user.model.User;

public class JwtUtil {

    private static final String JWT_CLAIM_FIELD_1 = "id";
    private static final String JWT_CLAIM_FIELD_2 = "email";
    private static final String JWT_SECRET = "jwt sign value";

    public static void verifyToken(String jwtToken) {
        JWT.require(Algorithm.HMAC512(JWT_SECRET)).build().verify(jwtToken);
    }

    public static String getToken(User user)  {
        String jwtToken = JWT.create()
                .withClaim(JWT_CLAIM_FIELD_1, user.getId())
                .withClaim(JWT_CLAIM_FIELD_2, user.getEmail())
                .sign(Algorithm.HMAC512(JWT_SECRET));
        return jwtToken;
    }
}
