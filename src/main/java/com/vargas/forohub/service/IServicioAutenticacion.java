package com.vargas.forohub.service;


import com.vargas.forohub.dto.RespuestaAutenticacion;
import com.vargas.forohub.dto.SolicitudAutenticacion;
import com.vargas.forohub.dto.UsuarioDto;

public interface IServicioAutenticacion {
    RespuestaAutenticacion registrar(UsuarioDto usuarioDto);
    RespuestaAutenticacion autenticar(SolicitudAutenticacion solicitud);
}