package com.ejemplo.resenasPeliculas.service;

import com.ejemplo.resenasPeliculas.model.Resena;
import com.ejemplo.resenasPeliculas.model.Pelicula;
import com.ejemplo.resenasPeliculas.repository.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que maneja la lógica de negocio de las reseñas.
 */

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    /**
     * Obtiene todas las reseñas almacenadas en la base de datos.
     *
     * @return Lista de reseñas.
     */
    // Obtener todas las reseñas
    public List<Resena> getAllResenas() {
        return resenaRepository.findAll();
    }

    // Obtener una reseña por id
    public Optional<Resena> getResenaById(Long id) {
        return resenaRepository.findById(id);
    }

    // Obtener reseñas de una película específica
    public List<Resena> getResenasByPeliculaId(Long peliculaId) {
        return resenaRepository.findByPeliculaId(peliculaId);
    }

    /**
     * Guarda una nueva reseña en la base de datos.
     *
     * @param resena La reseña a guardar.
     * @return La reseña guardada.
     */
    // Crear una nueva reseña
    public Resena createResena(Resena resena) {
        return resenaRepository.save(resena);
    }

    /**
     * Actualiza una nueva reseña en la base de datos.
     *
     * @param id     La id de la reseña.
     * @param resena La reseña a guardar.
     * @return La reseña guardada nueva.
     */
    // Actualizar una reseña existente
    public Resena updateResena(Long id, Resena resenaDetails) throws Exception {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new Exception("Reseña no encontrada con id " + id));
        resena.setContenido(resenaDetails.getContenido());
        resena.setRating(resenaDetails.getRating());
        return resenaRepository.save(resena);
    }

    /**
     * Elimina una reseña en la base de datos.
     */
    // Eliminar una reseña
    public void deleteResena(Long id) {
        resenaRepository.deleteById(id);
    }
}
