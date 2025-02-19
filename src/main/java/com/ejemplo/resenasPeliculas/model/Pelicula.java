package com.ejemplo.resenasPeliculas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Clase que representa una película en la plataforma.
 * Contiene detalles como el título, director, género, año de estreno y
 * sinopsis.
 */

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título de la película.
     */
    @NotBlank(message = "El titulo es obligatorio")
    @Column(nullable = false)
    private String titulo;

    /**
     * Nombre del director de la película.
     */
    @NotBlank(message = "El director es obligatorio")
    @Column(nullable = false)
    private String director;

    /**
     * Género de la película (ej. Acción, Comedia, Drama).
     */
    @NotBlank(message = "El genero es obligatorio")
    @Column(nullable = false)
    private String genero;

    /**
     * Año de estreno de la película.
     */
    @NotNull(message = "El año de estreno es obligatorio")
    @Column(nullable = false)
    private Integer anioEstreno;

    @Column(columnDefinition = "TEXT")
    private String sinopsis;

    private String imagenUrl;

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Pelicula() {

    }

    // Constructor
    public Pelicula(String titulo, String director, String genero, Integer anioEstreno, String sinopsis,
            String imagenUrl) {
        this.titulo = titulo;
        this.director = director;
        this.genero = genero;
        this.anioEstreno = anioEstreno;
        this.sinopsis = sinopsis;
        this.imagenUrl = imagenUrl;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getAnioEstreno() {
        return anioEstreno;
    }

    public void setAnioEstreno(Integer anioEstreno) {
        this.anioEstreno = anioEstreno;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

}
