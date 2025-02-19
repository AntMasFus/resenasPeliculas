package com.ejemplo.resenasPeliculas.repository;

import com.ejemplo.resenasPeliculas.model.Resena;
import com.ejemplo.resenasPeliculas.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la gestión de reseñas en la base de datos.
 */

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {

    // Método para buscar reseñas por la película
    List<Resena> findByPelicula(Pelicula pelicula);

    // Método para buscar reseñas por el ID de la película
    List<Resena> findByPeliculaId(Long peliculaId);
}
