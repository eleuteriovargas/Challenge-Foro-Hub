package com.vargas.forohub.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class TokenService {

    private static final String CLAVE_SECRETA = "fwQMSEgZ98hK+A/mw9Dkce24Btvy3QCRKT/GZarU90I=\n";


    private static final String ISSUER = "Forohub";

    public String generarToken(UserDetails detallesUsuario) {
        return generarToken(new HashMap<>(), detallesUsuario);
    }

    public String generarToken(Map<String, Object> claimsExtra, UserDetails detallesUsuario) {
        return Jwts.builder()
                .setClaims(claimsExtra)
                .setSubject(detallesUsuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas
                .signWith(obtenerClaveFirma(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraerEmail(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    public <T> T extraerClaim(String token, Function<Claims, T> resolverClaims) {
        final Claims claims = extraerTodosLosClaims(token);
        return resolverClaims.apply(claims);
    }

    public boolean esTokenValido(String token, UserDetails detallesUsuario) {
        final String email = extraerEmail(token);
        return (email.equals(detallesUsuario.getUsername())) && !esTokenExpirado(token);
    }

    private boolean esTokenExpirado(String token) {
        return extraerExpiracion(token).before(new Date());
    }

    private Date extraerExpiracion(String token) {
        return extraerClaim(token, Claims::getExpiration);
    }

    private Claims extraerTodosLosClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(obtenerClaveFirma())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key obtenerClaveFirma() {
        byte[] bytesClave = Decoders.BASE64.decode(CLAVE_SECRETA);
        return Keys.hmacShaKeyFor(bytesClave);
    }

    public String getSubject(String tokenJWT) {
        try {
            System.out.println("Validando token: " + tokenJWT); // Log para debug
            var algoritmo = Algorithm.HMAC256(CLAVE_SECRETA);
            return JWT.require(algoritmo)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            System.err.println("Error validando token: " + exception.getMessage());
            throw new RuntimeException("Token inválido o expirado");
        }
    }
}

