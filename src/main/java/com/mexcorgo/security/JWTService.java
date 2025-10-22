package com.mexcorgo.security;


import com.mexcorgo.datamodel.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .filter(auth-> auth.startsWith("ROLE_"))
                .map(auth->auth.replace("ROLE_",""))
                .toList();

        if(!roles.equals("Admin")){

            List<String> department = userDetails.getAuthorities().stream()
                    .map(authority -> authority.getAuthority())
                    .filter(auth-> auth.startsWith("DEPT_"))
                    .map(auth->auth.replace("DEPT_",""))
                    .toList();

            claims.put("departments",department);
        }



        claims.put("roles",roles);



        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+86400000))
                .and()
                .signWith(getKey())
                .compact();
    }


    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String extractEmailId(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean validateToken(String token,UserDetails userDetails){
        final String emailId = extractEmailId(token);
        return (emailId.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    public List<String> extractRole(String token){
        List<String> roleString = extractClaims(token,claims->claims.get("roles",List.class));

        return roleString;

    }

    public List<String> extractDepartment(String token){
        List<String> roleString = extractClaims(token,claims->claims.get("departments",List.class));

        return roleString;
    }
}
