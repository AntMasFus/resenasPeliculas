package com.ejemplo.resenasPeliculas.controller;

import com.ejemplo.resenasPeliculas.model.Resena;
import com.ejemplo.resenasPeliculas.model.Usuario;
import com.ejemplo.resenasPeliculas.service.ResenaService;
import com.ejemplo.resenasPeliculas.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para gestionar las reseñas en la aplicación.
 */
@RestController
@RequestMapping("/api")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtiene todas las reseñas registradas en la base de datos.
     *
     * @return Una lista de todas las reseñas.
     */
    @GetMapping("/resenas")
    public ResponseEntity<List<Resena>> getAllResenas() {
        return ResponseEntity.ok(resenaService.getAllResenas());
    }

    /**
     * Obtiene una reseña específica por su ID.
     *
     * @param id El identificador único de la reseña.
     * @return La reseña encontrada o un mensaje de error si no existe.
     */
    @GetMapping("/resenas/{id}")
    public ResponseEntity<?> getResenaById(@PathVariable Long id) {
        return resenaService.getResenaById(id)
                .map(resena -> ResponseEntity.ok((Object) resena))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reseña no encontrada"));
    }

    /**
     * Obtiene todas las reseñas asociadas a una película específica.
     *
     * @param peliculaId El identificador único de la película.
     * @return Una lista de reseñas asociadas a la película.
     */
    @GetMapping("/peliculas/{peliculaId}/resenas")
    public ResponseEntity<List<Resena>> getResenasByPeliculaId(@PathVariable Long peliculaId) {
        return ResponseEntity.ok(resenaService.getResenasByPeliculaId(peliculaId));
    }

    /**
     * Crea una nueva reseña en la base de datos.
     *
     * @param resena        La reseña a crear.
     * @param bindingResult Resultado de la validación de la entrada.
     * @param principal     Información del usuario autenticado.
     * @return La reseña creada o un mensaje de error en caso de datos inválidos.
     */
    @PostMapping("/resenas")
    public ResponseEntity<?> createResena(
            @Valid @RequestBody Resena resena,
            BindingResult bindingResult,
            Principal principal) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        // Obtener el usuario autenticado desde el token
        String username = principal.getName();
        Optional<Usuario> usuarioOptional = usuarioService.findByUsername(username);

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        Usuario usuario = usuarioOptional.get();
        resena.setUsuario(usuario); // Asociar el usuario autenticado a la reseña

        Resena nuevaResena = resenaService.createResena(resena);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaResena);
    }

    /**
     * Actualiza una reseña existente en la base de datos.
     *
     * @param id            El identificador único de la reseña a actualizar.
     * @param resenaDetails Los nuevos datos de la reseña.
     * @param bindingResult Resultado de la validación de la entrada.
     * @return La reseña actualizada o un mensaje de error si la reseña no existe.
     */
    @PutMapping("/resenas/{id}")
    public ResponseEntity<?> updateResena(
            @PathVariable Long id,
            @Valid @RequestBody Resena resenaDetails,
            BindingResult bindingResult,
            Principal principal) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Optional<Resena> existingResenaOptional = resenaService.getResenaById(id);
        if (existingResenaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reseña no encontrada");
        }

        Resena existingResena = existingResenaOptional.get();
        Usuario usuarioAutenticado = usuarioService.findByUsername(principal.getName()).orElse(null);

        if (usuarioAutenticado == null || !existingResena.getUsuario().getId().equals(usuarioAutenticado.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes actualizar esta reseña.");
        }

        try {
            existingResena.setContenido(resenaDetails.getContenido());
            existingResena.setRating(resenaDetails.getRating());
            Resena resenaActualizada = resenaService.updateResena(id, existingResena);
            return ResponseEntity.ok(resenaActualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la reseña.");
        }
    }

    /**
     * Elimina una reseña de la base de datos si pertenece al usuario autenticado.
     *
     * @param id        El identificador único de la reseña a eliminar.
     * @param principal Información del usuario autenticado.
     * @return Un mensaje de éxito si se eliminó correctamente o un mensaje de error
     *         si la reseña no existe.
     */
    @DeleteMapping("/resenas/{id}")
    public ResponseEntity<?> deleteResena(@PathVariable Long id, Principal principal) {
        Optional<Resena> resenaOptional = resenaService.getResenaById(id);

        if (resenaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reseña no encontrada");
        }

        Resena resena = resenaOptional.get();
        Usuario usuarioAutenticado = usuarioService.findByUsername(principal.getName()).orElse(null);

        if (usuarioAutenticado == null || !resena.getUsuario().getId().equals(usuarioAutenticado.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes eliminar esta reseña.");
        }

        resenaService.deleteResena(id);
        return ResponseEntity.ok("Reseña eliminada exitosamente");
    }
}
