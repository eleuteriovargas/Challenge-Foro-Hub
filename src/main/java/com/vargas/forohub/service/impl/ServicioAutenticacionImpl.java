package com.vargas.forohub.service.impl;


import com.vargas.forohub.dto.RespuestaAutenticacion;
import com.vargas.forohub.dto.SolicitudAutenticacion;
import com.vargas.forohub.dto.UsuarioDto;
import com.vargas.forohub.model.Usuario;
import com.vargas.forohub.model.Usuario.RolUsuario;
import com.vargas.forohub.repository.RepositorioUsuario;
import com.vargas.forohub.segurity.ServicioJwt;
import com.vargas.forohub.service.IServicioAutenticacion;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioAutenticacionImpl implements IServicioAutenticacion {

    private final RepositorioUsuario repositorioUsuario;
    private final PasswordEncoder passwordEncoder;
    private final ServicioJwt servicioJwt;
    private final AuthenticationManager authenticationManager;

    @Override
    public RespuestaAutenticacion registrar(UsuarioDto usuarioDto) {
        var usuario = Usuario.builder()
                .nombre(usuarioDto.getNombre())
                .email(usuarioDto.getEmail())
                .clave(passwordEncoder.encode(usuarioDto.getClave()))
                .rol(RolUsuario.USUARIO)
                .build();

        repositorioUsuario.save(usuario);

        var jwtToken = servicioJwt.generarToken(usuario);
        return RespuestaAutenticacion.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public RespuestaAutenticacion autenticar(SolicitudAutenticacion solicitud) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        solicitud.getEmail(),
                        solicitud.getClave()
                )
        );

        var usuario = repositorioUsuario.findByEmail(solicitud.getEmail())
                .orElseThrow();

        var jwtToken = servicioJwt.generarToken(usuario);
        return RespuestaAutenticacion.builder()
                .token(jwtToken)
                .build();
    }
}