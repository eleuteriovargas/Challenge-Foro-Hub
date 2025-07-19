package com.vargas.forohub.segurity;

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
public class ServicioJwt {

    private static final String CLAVE_SECRETA = "tu_clave_secreta_muy_larga_y_compleja_para_que_sea_segura_256_bits";

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
}

