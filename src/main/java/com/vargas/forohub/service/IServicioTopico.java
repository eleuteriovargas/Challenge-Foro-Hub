package com.vargas.forohub.service;

import com.vargas.forohub.dto.TopicoDto;

import java.util.List;

public interface IServicioTopico {
    List<TopicoDto> getTopicos();
    TopicoDto getTopicoPorId(Long id);
    TopicoDto crearTopico(TopicoDto topicoDto, String emailUsuario);
    TopicoDto actualizarTopico(TopicoDto topicoDto, String emailUsuario);
    void eliminarTopico(Long id, String emailUsuario);
}