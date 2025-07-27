package com.vargas.forohub.service.impl;


import com.vargas.forohub.dto.RespuestaAutenticacion;
import com.vargas.forohub.dto.SolicitudAutenticacion;
import com.vargas.forohub.dto.UsuarioDto;
import com.vargas.forohub.domain.usuario.Usuario;
import com.vargas.forohub.domain.usuario.Usuario.RolUsuario;
import com.vargas.forohub.domain.usuario.UsuarioRepository;
import com.vargas.forohub.infra.security.TokenService;
import com.vargas.forohub.service.IServicioAutenticacion;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioAutenticacionImpl implements IServicioAutenticacion {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public RespuestaAutenticacion registrar(UsuarioDto usuarioDto) {
        var usuario = Usuario.builder()
                .nombre(usuarioDto.getNombre())
                .email(usuarioDto.getEmail())
                .clave(passwordEncoder.encode(usuarioDto.getClave()))
                .rol(RolUsuario.USUARIO)
                .build();

        usuarioRepository.save(usuario);

        var jwtToken = tokenService.generarToken(usuario);
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

        var usuario = usuarioRepository.findByEmail(solicitud.getEmail())
                .orElseThrow();

        var jwtToken = tokenService.generarToken(usuario);
        return RespuestaAutenticacion.builder()
                .token(jwtToken)
                .build();
    }
}