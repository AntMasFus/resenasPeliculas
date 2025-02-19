package com.ejemplo.resenasPeliculas.controller;

import com.ejemplo.resenasPeliculas.model.Pelicula;
import com.ejemplo.resenasPeliculas.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador para gestionar las películas en la aplicación.
 */
@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    /**
     * Obtiene todas las películas disponibles.
     * 
     * @return Lista de películas.
     */
    // Endpoint para listar todas las películas
    @GetMapping
    public ResponseEntity<List<Pelicula>> getAllPeliculas() {
        return ResponseEntity.ok(peliculaService.getAllPeliculas());
    }

    /**
     * Obtiene una película por su ID.
     * 
     * @param id ID de la película.
     * @return La película encontrada.
     */
    // Endpoint para obtener una película por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getPeliculaById(@PathVariable Long id) {
        return peliculaService.getPeliculaById(id)
                .map(pelicula -> ResponseEntity.ok((Object) pelicula))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pelicula no encontrada"));
    }

    /**
     * Crea una nueva película.
     * 
     * @param pelicula Datos de la película a crear.
     * @return La película creada.
     */
    // Endpoint para crear una nueva película
    @PostMapping
    public ResponseEntity<?> createPelicula(@Valid @RequestBody Pelicula pelicula, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        if (pelicula.getImagenUrl() == null || pelicula.getImagenUrl().isEmpty()) {
            pelicula.setImagenUrl("https://via.placeholder.com/300x400?text=No+Image"); // Imagen por defecto
        }

        Pelicula nuevaPelicula = peliculaService.createPelicula(pelicula);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPelicula);
    }

    /**
     * Actualizar una película.
     * 
     * @param id       id de la película a actualizar.
     * @param Pelicula nombre pelicula
     * @return La película creada.
     */
    // Endpoint para actualizar una película existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePelicula(@PathVariable Long id, @Valid @RequestBody Pelicula peliculaDetails,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            Pelicula peliculaActualizada = peliculaService.updatePelicula(id, peliculaDetails);
            return ResponseEntity.ok(peliculaActualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Eliminar una película.
     * 
     * @param id id de la película a eliminar.
     * @return La película creada.
     */
    // Endpoint para eliminar una película
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePelicula(@PathVariable Long id) {
        try {
            peliculaService.deletePelicula(id);
            return ResponseEntity.ok("Pelicula eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pelicula no encontrada");
        }
    }
}
