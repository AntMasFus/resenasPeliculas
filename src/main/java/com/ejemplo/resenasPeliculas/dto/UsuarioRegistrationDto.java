package com.ejemplo.resenasPeliculas.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) para la solicitud de registro de un usuario.
 * <p>
 * Contiene los campos necesarios para registrar un nuevo usuario en la
 * aplicación.
 */
public class UsuarioRegistrationDto {

    /**
     * Nombre de usuario del nuevo usuario.
     * No puede estar vacío.
     */
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;

    /**
     * Dirección de correo electrónico del nuevo usuario.
     * No puede estar vacía.
     */
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    private String email;

    /**
     * Contraseña del nuevo usuario.
     * No puede estar vacía.
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    /**
     * Constructor vacío para la creación de un objeto UsuarioRegistrationDto.
     */
    public UsuarioRegistrationDto() {
    }

    /**
     * Constructor con parámetros para la creación de un objeto
     * UsuarioRegistrationDto.
     *
     * @param username Nombre de usuario.
     * @param email    Dirección de correo electrónico.
     * @param password Contraseña del usuario.
     */
    public UsuarioRegistrationDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return Nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param username Nombre de usuario.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene la dirección de correo electrónico.
     *
     * @return Dirección de correo electrónico.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece la dirección de correo electrónico.
     *
     * @param email Dirección de correo electrónico.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return Contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password Contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
