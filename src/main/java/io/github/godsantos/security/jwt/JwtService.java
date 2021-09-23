package io.github.godsantos.security.jwt;

import io.github.godsantos.domain.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.key-signature}")
    private String keySignature;

    public String generateToken(UserEntity userEntity){
        long expString = Long.valueOf(expiration);
        LocalDateTime dataTimeExpiration = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dataTimeExpiration.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts
                .builder()
                .setSubject(userEntity.getLogin())
                .setExpiration(data)
                .signWith( SignatureAlgorithm.HS512, keySignature )
                .compact();
    }

    private Claims getClaims(String token ) throws ExpiredJwtException {
        return Jwts
                 .parser()
                 .setSigningKey(keySignature)
                 .parseClaimsJws(token)
                 .getBody();
    }

    public boolean tokenValido( String token ){
        try{
            Claims claims = getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime data =
                    expirationDate.toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        }catch (Exception e){
            return false;
        }
    }

    public String getUserLogin(String token) throws ExpiredJwtException{
        return (String) getClaims(token).getSubject();
    }
}
