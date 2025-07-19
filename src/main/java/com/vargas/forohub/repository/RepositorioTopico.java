package com.vargas.forohub.repository;


import com.vargas.forohub.model.Topico;
import com.vargas.forohub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioTopico extends JpaRepository<Topico, Long> {
    @Query("SELECT t FROM Topico t WHERE t.autor.id = :authorId")
    List<Topico> findByAuthorId(@Param("authorId") Long authorId);
}
