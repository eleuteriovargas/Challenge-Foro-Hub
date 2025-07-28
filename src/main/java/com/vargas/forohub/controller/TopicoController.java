package com.vargas.forohub.controller;

import com.vargas.forohub.dto.TopicoDto;
import com.vargas.forohub.service.IServicioTopico;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    private final IServicioTopico servicioTopico;

    @Autowired
    public TopicoController(IServicioTopico servicioTopico) {
        this.servicioTopico = servicioTopico;
    }

//    @Operation(
//            summary = "Listar todos los tópicos",
//            description = "Obtiene una lista paginada de todos los tópicos disponibles"
//    )
    @ApiResponse(responseCode = "200", description = "Lista de tópicos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<TopicoDto>> listarTopicos() {
        return ResponseEntity.ok(servicioTopico.getTopicos());
    }

//    @Operation(
//            summary = "Obtener tópico por ID",
//            description = "Recupera un tópico específico por su identificador único"
//    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tópico encontrado"),
            @ApiResponse(responseCode = "404", description = "Tópico no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TopicoDto> obtenerTopico(
            @Parameter(description = "ID del tópico a buscar", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(servicioTopico.getTopicoPorId(id));
    }

//    @Operation(
//            summary = "Crear nuevo tópico",
//            description = "Crea un nuevo tópico en el foro (requiere autenticación)"
//    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tópico creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos del tópico inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PostMapping
    public ResponseEntity<TopicoDto> crearTopico(
            @RequestBody @Valid TopicoDto topicoDto,
            @AuthenticationPrincipal UserDetails usuario,
            UriComponentsBuilder uriBuilder) {

        TopicoDto topicoCreado = servicioTopico.crearTopico(topicoDto, usuario.getUsername());

        URI ubicacion = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(topicoCreado.getId())
                .toUri();

        return ResponseEntity.created(ubicacion).body(topicoCreado);
    }

    @Operation(
            summary = "Actualizar tópico",
            description = "Actualiza tópico exixtente en el foro (requiere autenticación)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tópico actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos del tópico inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TopicoDto> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid TopicoDto topicoDto,
            @AuthenticationPrincipal UserDetails usuario) {

        topicoDto.setId(id);
        return ResponseEntity.ok(servicioTopico.actualizarTopico(topicoDto, usuario.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails usuario) {

        servicioTopico.eliminarTopico(id, usuario.getUsername());
        return ResponseEntity.noContent().build();
    }
}