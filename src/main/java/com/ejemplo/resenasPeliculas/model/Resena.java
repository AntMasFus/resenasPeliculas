package com.ejemplo.resenasPeliculas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Clase que representa una reseña escrita por un usuario sobre una película.
 * Contiene el contenido de la reseña, una calificación y relaciones con Usuario
 * y Pelicula.
 */

@Entity
@Table(name = "resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Contenido de la reseña escrita por el usuario.
     */
    @NotBlank(message = "El contenido es obligatorio")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;

    /**
     * Calificación de la película (de 1 a 5 estrellas).
     */
    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    @Column(nullable = false)
    private Integer rating;

    /**
     * Película sobre la que se ha realizado la reseña.
     */
    // Relación con Pelicula (muchas reseñas para una película)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;

    /**
     * Usuario que ha escrito la reseña.
     */
    // Relación con Usuario (muchas reseñas escritas por un usuario)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Constructor por defecto
    public Resena() {
    }

    // Constructor con parámetros
    public Resena(String contenido, Integer rating, Pelicula pelicula, Usuario usuario) {
        this.contenido = contenido;
        this.rating = rating;
        this.pelicula = pelicula;
        this.usuario = usuario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
