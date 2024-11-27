package com.sooraj.accountservice.services;

import com.sooraj.accountservice.dto.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    String secretKey = """
            wtdBxJ93+xCiRP+z2Y9mAcETeV3q4A6PTuxSloITuIzE2PSpJqXamvg6QwnKl9qZKJb/
            TYD+Hj8veFkI4DuHhYKfY3cVyVLWo/rCDEHmzgpgwiCr5TTn7WpvFqqxBkiWP8KOm4Nmhyjv2Eaws+
            ySYIXCSu+xjK7IuRsV4ZZpnQYYCnMkNmQuP9lZyOhsAzzn
            """.replaceAll("\\s", "");

    public JwtToken generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        return new JwtToken(Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+15*60*1000))
                .signWith(getKey())
                .compact());
    }

    private SecretKey getKey() {
        byte [] keyBites = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBites);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        return (!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}