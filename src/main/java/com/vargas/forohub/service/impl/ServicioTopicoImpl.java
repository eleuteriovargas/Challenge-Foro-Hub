package com.vargas.forohub.service.impl;


import com.vargas.forohub.dto.TopicoDto;
import com.vargas.forohub.model.Topico;
import com.vargas.forohub.model.Usuario;
import com.vargas.forohub.exception.AccesoDenegadoException;
import com.vargas.forohub.exception.RecursoNoEncontradoException;
import com.vargas.forohub.exception.NoAutorizadoException;
import com.vargas.forohub.repository.RepositorioTopico;
import com.vargas.forohub.repository.RepositorioUsuario;
import com.vargas.forohub.service.IServicioTopico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicioTopicoImpl implements IServicioTopico {


    //List<Topico> topicos = repositorioTopico.findByAutor(usuario);

    private final RepositorioTopico repositorioTopico;
    private final RepositorioUsuario repositorioUsuario;

    @Override
    public List<TopicoDto> getTopicos() {
        return repositorioTopico.findAll()
                .stream()
                .map(TopicoDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public TopicoDto getTopicoPorId(Long id) {
        Topico topico = repositorioTopico.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tópico no encontrado con id: " + id));
        return new TopicoDto(topico);
    }

    @Override
    public TopicoDto crearTopico(TopicoDto topicoDto, String emailUsuario) {
        Usuario autor = repositorioUsuario.findByEmail(emailUsuario)
                .orElseThrow(() -> new NoAutorizadoException("Usuario no encontrado"));

        Topico topico = new Topico();
        topico.setTitulo(topicoDto.getTitulo());
        topico.setContenido(topicoDto.getContenido());
        topico.setAutor(autor);

        Topico topicoGuardado = repositorioTopico.save(topico);
        return new TopicoDto(topicoGuardado);
    }

    @Override
    public TopicoDto actualizarTopico(TopicoDto topicoDto, String emailUsuario) {
        Topico topicoExistente = repositorioTopico.findById(topicoDto.getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tópico no encontrado con id: " + topicoDto.getId()));

        Usuario usuarioActual = repositorioUsuario.findByEmail(emailUsuario)
                .orElseThrow(() -> new NoAutorizadoException("Usuario no encontrado"));

        // Solo el autor o un administrador puede actualizar el tópico
        if (!topicoExistente.getAutor().getId().equals(usuarioActual.getId()) &&
                !usuarioActual.getRol().equals(Usuario.RolUsuario.ADMINISTRADOR)) {
            throw new AccesoDenegadoException("No tienes permiso para actualizar este tópico");
        }

        topicoExistente.setTitulo(topicoDto.getTitulo());
        topicoExistente.setContenido(topicoDto.getContenido());
        topicoExistente.setEstado(topicoDto.getEstado());

        Topico topicoActualizado = repositorioTopico.save(topicoExistente);
        return new TopicoDto(topicoActualizado);
    }

    @Override
    public void eliminarTopico(Long id, String emailUsuario) {
        Topico topico = repositorioTopico.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tópico no encontrado con id: " + id));

        Usuario usuarioActual = repositorioUsuario.findByEmail(emailUsuario)
                .orElseThrow(() -> new NoAutorizadoException("Usuario no encontrado"));

        // Solo el autor o un administrador puede eliminar el tópico
        if (!topico.getAutor().getId().equals(usuarioActual.getId()) &&
                !usuarioActual.getRol().equals(Usuario.RolUsuario.ADMINISTRADOR)) {
            throw new AccesoDenegadoException("No tienes permiso para eliminar este tópico");
        }

        repositorioTopico.delete(topico);
    }
}
