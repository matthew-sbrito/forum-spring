package br.com.techsoft.forum.services;

import br.com.techsoft.forum.models.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String encode(Authentication authentication) {
        Usuario user = (Usuario) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("Api forum Tech soft")
                .setSubject(user.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Claims decoded(String token) {
       try {
           Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
           return claimsJws.getBody();
       } catch (Exception e) {
           return null;
       }
    }

    public Long getIdUser(String token) {
        Claims claims = decoded(token);
        return Long.parseLong(claims.getSubject());
    }
}
