package com.ejemplo.resenasPeliculas.service;

import com.ejemplo.resenasPeliculas.model.Pelicula;
import com.ejemplo.resenasPeliculas.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que maneja la lógica de negocio de las películas.
 */
@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    /**
     * Obtiene todas las películas almacenadas en la base de datos.
     *
     * @return Lista de películas.
     */
    // Obtener todas las películas
    public List<Pelicula> getAllPeliculas() {
        return peliculaRepository.findAll();
    }

    /**
     * Obtiene la película almacenadas en la base de datos con ese ID.
     *
     * @return Lista de películas por una ID.
     */
    // Obtener una película por su id
    public Optional<Pelicula> getPeliculaById(Long id) {
        return peliculaRepository.findById(id);
    }

    /**
     * Guarda una nueva película en la base de datos.
     *
     * @param pelicula La película a guardar.
     * @return La película guardada.
     */
    // crear una nueva película
    public Pelicula createPelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    // actualizar una película existente
    public Pelicula updatePelicula(Long id, Pelicula peliculaDetails) throws Exception {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new Exception("Pelicula no encontrada con id " + id));
        pelicula.setTitulo(peliculaDetails.getTitulo());
        pelicula.setDirector(peliculaDetails.getDirector());
        pelicula.setGenero(peliculaDetails.getGenero());
        pelicula.setAnioEstreno(peliculaDetails.getAnioEstreno());
        pelicula.setSinopsis(peliculaDetails.getSinopsis());
        return peliculaRepository.save(pelicula);
    }

    // Eliminar una película
    public void deletePelicula(Long id) {
        peliculaRepository.deleteById(id);
    }
}
