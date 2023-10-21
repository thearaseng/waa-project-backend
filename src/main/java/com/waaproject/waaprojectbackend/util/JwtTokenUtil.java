package com.waaproject.waaprojectbackend.util;

import com.waaproject.waaprojectbackend.constant.Role;
import com.waaproject.waaprojectbackend.model.Customer;
import com.waaproject.waaprojectbackend.model.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${app.jwt.secret}")
    private String SECRET;

    @Value("${app.jwt.expire.duration}")
    private long EXPIRE_DURATION;

    public String generateToken(User user) {

        String role = user.getClass() == Customer.class ? Role.USER.getName() : Role.SELLER.getName();

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer("MIU")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e){
            log.error(e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public Claims parseClaims(String token){
        return Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();
    }

}

