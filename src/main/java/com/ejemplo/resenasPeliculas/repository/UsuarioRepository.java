package com.ejemplo.resenasPeliculas.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ejemplo.resenasPeliculas.model.Usuario;

/**
 * Repositorio JPA para la gestión de usuarios en la base de datos.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Usuario> findByUsername(String username);
}
