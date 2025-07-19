package com.vargas.forohub.repository;



import com.vargas.forohub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
