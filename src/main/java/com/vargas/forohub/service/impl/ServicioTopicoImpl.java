package com.vargas.forohub.service.impl;


import com.vargas.forohub.dto.TopicoDto;
import com.vargas.forohub.domain.topico.Topico;
import com.vargas.forohub.domain.usuario.Usuario;
import com.vargas.forohub.infra.exception.AccesoDenegadoException;
import com.vargas.forohub.infra.exception.RecursoNoEncontradoException;
import com.vargas.forohub.infra.exception.NoAutorizadoException;
import com.vargas.forohub.domain.topico.TopicoRepository;
import com.vargas.forohub.domain.usuario.UsuarioRepository;
import com.vargas.forohub.service.IServicioTopico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicioTopicoImpl implements IServicioTopico {


    //List<Topico> topicos = repositorioTopico.findByAutor(usuario);

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<TopicoDto> getTopicos() {
        return topicoRepository.findAll()
                .stream()
                .map(TopicoDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public TopicoDto getTopicoPorId(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tópico no encontrado con id: " + id));
        return new TopicoDto(topico);
    }

    @Override
    public TopicoDto crearTopico(TopicoDto topicoDto, String emailUsuario) {
        Usuario autor = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new NoAutorizadoException("Usuario no encontrado"));

        Topico topico = new Topico();
        topico.setTitulo(topicoDto.getTitulo());
        topico.setContenido(topicoDto.getContenido());
        topico.setAutor(autor);

        Topico topicoGuardado = topicoRepository.save(topico);
        return new TopicoDto(topicoGuardado);
    }

    @Override
    public TopicoDto actualizarTopico(TopicoDto topicoDto, String emailUsuario) {
        Topico topicoExistente = topicoRepository.findById(topicoDto.getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tópico no encontrado con id: " + topicoDto.getId()));

        Usuario usuarioActual = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new NoAutorizadoException("Usuario no encontrado"));

        // Solo el autor o un administrador puede actualizar el tópico
        if (!topicoExistente.getAutor().getId().equals(usuarioActual.getId()) &&
                !usuarioActual.getRol().equals(Usuario.RolUsuario.ADMINISTRADOR)) {
            throw new AccesoDenegadoException("No tienes permiso para actualizar este tópico");
        }

        topicoExistente.setTitulo(topicoDto.getTitulo());
        topicoExistente.setContenido(topicoDto.getContenido());
        topicoExistente.setEstado(topicoDto.getEstado());

        Topico topicoActualizado = topicoRepository.save(topicoExistente);
        return new TopicoDto(topicoActualizado);
    }

    @Override
    public void eliminarTopico(Long id, String emailUsuario) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tópico no encontrado con id: " + id));

        Usuario usuarioActual = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new NoAutorizadoException("Usuario no encontrado"));

        // Solo el autor o un administrador puede eliminar el tópico
        if (!topico.getAutor().getId().equals(usuarioActual.getId()) &&
                !usuarioActual.getRol().equals(Usuario.RolUsuario.ADMINISTRADOR)) {
            throw new AccesoDenegadoException("No tienes permiso para eliminar este tópico");
        }

        topicoRepository.delete(topico);
    }
}
