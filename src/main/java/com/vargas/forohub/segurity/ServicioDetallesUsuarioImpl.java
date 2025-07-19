package com.vargas.forohub.segurity;

import com.vargas.forohub.model.Usuario;
import com.vargas.forohub.repository.RepositorioUsuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ServicioDetallesUsuarioImpl implements UserDetailsService {

    private final RepositorioUsuario repositorioUsuario;

    public ServicioDetallesUsuarioImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repositorioUsuario.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return usuario;
    }
}
