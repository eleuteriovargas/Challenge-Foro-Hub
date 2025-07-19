package com.vargas.forohub.segurity;



import com.vargas.forohub.segurity.ServicioJwt;
import com.vargas.forohub.segurity.ServicioDetallesUsuarioImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroAutenticacionJwt extends OncePerRequestFilter {

    private final ServicioJwt servicioJwt;
    private final ServicioDetallesUsuarioImpl servicioDetallesUsuario;

    public FiltroAutenticacionJwt(ServicioJwt servicioJwt,
                                  ServicioDetallesUsuarioImpl servicioDetallesUsuario) {
        this.servicioJwt = servicioJwt;
        this.servicioDetallesUsuario = servicioDetallesUsuario;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String emailUsuario;

        // Validar el encabezado de autorización
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token JWT
        jwt = authHeader.substring(7);
        emailUsuario = servicioJwt.extraerEmail(jwt);

        // Validar token y autenticar al usuario
        if (emailUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails detallesUsuario = this.servicioDetallesUsuario.loadUserByUsername(emailUsuario);

            if (servicioJwt.esTokenValido(jwt, detallesUsuario)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        detallesUsuario,
                        null,
                        detallesUsuario.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}