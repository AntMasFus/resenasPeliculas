package com.ejemplo.resenasPeliculas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Clase que representa a un usuario dentro de la aplicación.
 * Contiene información básica como nombre de usuario, email y contraseña.
 */

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario único.
     */
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Correo electrónico del usuario, único en la base de datos.
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Contraseña cifrada del usuario.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Column(nullable = false)
    private String password;

    // Constructor por defecto
    public Usuario() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
