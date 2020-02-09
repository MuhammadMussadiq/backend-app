package com.assignemnt.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.List;

public class JwtUtils {

    public static final Integer ACCESS_TOKEN_EXPIRY = 3600000; // 1 hour
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SECRET_KEY = "secret";


    public static String generateAccessToken(String subject, List<String> authorities) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY))
                .withArrayClaim("roles", authorities.toArray(new String[0]))
                .sign(Algorithm.HMAC512(SECRET_KEY.getBytes()));
    }


    public static String getSubjectFromValidToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(SECRET_KEY))
                    .build()
                    .verify(token.replace(JwtUtils.TOKEN_PREFIX, ""))
                    .getSubject();

        } catch (Exception e) {
            e.printStackTrace();
            //return null if token is not valid
            return null;
        }
    }

    public static List<String> getUserRoles(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(token.replace(JwtUtils.TOKEN_PREFIX, ""))
                .getClaim("roles").asList(String.class);
    }
}
