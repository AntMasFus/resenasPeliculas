package com.ejemplo.resenasPeliculas.repository;

import com.ejemplo.resenasPeliculas.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la gestión de peliculas en la base de datos.
 */
@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

}
