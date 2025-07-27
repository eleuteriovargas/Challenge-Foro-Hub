package com.vargas.forohub.dto;


import com.vargas.forohub.domain.topico.Topico;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicoDto {

    private Long id;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El contenido es obligatorio")
    private String contenido;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private Topico.EstadoTopico estado;
    private Long autorId;
    private String nombreAutor;

    public TopicoDto(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.contenido = topico.getContenido();
        this.fechaCreacion = topico.getFechaCreacion();
        this.fechaActualizacion = topico.getFechaActualizacion();
        this.estado = topico.getEstado();
        this.autorId = topico.getAutor().getId();
        this.nombreAutor = topico.getAutor().getNombre();
    }
}