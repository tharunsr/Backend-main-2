package com.example.SS.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    public String secretKey ;

    public JWTService() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk = keygen.generateKey();
        secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
    }


    public String generateToken(String email,String role) {

        Map<String,Object> claims = new HashMap<>();
        claims.put("email",email);
        claims.put("role", role);
        return Jwts.builder()
                .addClaims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*30*1000))
                .signWith(getkey())
                .compact();


//        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0aGFydW5zciIsIm5hbWUiOiJKb2huIERvZSIsImlhdCI6MTcyOTIxOTA5OSwiZXhwIjoxNzQ5MjE5MDk5fQ.wLWN-kXUy5CD3LFQw27Zw4rZnHkNovgsEsAzipzrY7k";
    }
    public SecretKey getkey(){
        byte[] ans = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(ans);
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userEmail = extractEmail(token);
        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractEmail(String token) {
    return extractClaim(token,claims -> claims.get("email",String.class));
    }

    public String extractRole(String token) {  // ✅ Extract role
        return extractClaim(token, claims -> claims.get("role", String.class));
    }
}


