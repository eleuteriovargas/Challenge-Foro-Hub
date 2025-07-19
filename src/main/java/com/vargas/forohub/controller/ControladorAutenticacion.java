package com.vargas.forohub.controller;


import com.vargas.forohub.dto.SolicitudAutenticacion;
import com.vargas.forohub.dto.RespuestaAutenticacion;
import com.vargas.forohub.dto.UsuarioDto;
import com.vargas.forohub.service.IServicioAutenticacion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autenticacion")
@Tag(name = "Autenticación", description = "API para registro y autenticación de usuarios")
@RequiredArgsConstructor
public class ControladorAutenticacion {

    private final IServicioAutenticacion servicioAutenticacion;

    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Endpoint para registrar un nuevo usuario en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping("/registro")
    public ResponseEntity<RespuestaAutenticacion> registrar(
            @RequestBody @Valid UsuarioDto usuarioDto) {
        return ResponseEntity.ok(servicioAutenticacion.registrar(usuarioDto));
    }

    @Operation(
            summary = "Iniciar sesión",
            description = "Endpoint para autenticar usuarios y obtener token JWT"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/iniciar-sesion")
    public ResponseEntity<RespuestaAutenticacion> autenticar(
            @RequestBody @Valid SolicitudAutenticacion solicitud) {
        return ResponseEntity.ok(servicioAutenticacion.autenticar(solicitud));
    }
}
