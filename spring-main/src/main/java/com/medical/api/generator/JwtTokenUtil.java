package com.medical.api.generator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwtTokenUtil {

    private String secret = "0cO07vQyKtT/sBFSAOl7rIWAfaJah//ikKrbP0lLmLQ=";

    public String generateToken(UserDetails userDetails) {
        long expirationtime = 1000 * 60 * 60;
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationtime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
