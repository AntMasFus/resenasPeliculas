package com.ejemplo.resenasPeliculas.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) para la solicitud de inicio de sesión de un
 * usuario.
 * <p>
 * Contiene los campos necesarios para autenticar a un usuario en la aplicación.
 */
public class LoginRequestDto {

    /**
     * Nombre de usuario del usuario que intenta iniciar sesión.
     * No puede estar vacío.
     */
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;

    /**
     * Contraseña del usuario que intenta iniciar sesión.
     * No puede estar vacía.
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    /**
     * Obtiene el nombre de usuario del usuario que intenta iniciar sesión.
     *
     * @return Nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario del usuario que intenta iniciar sesión.
     *
     * @param username Nombre de usuario.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene la contraseña del usuario que intenta iniciar sesión.
     *
     * @return Contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario que intenta iniciar sesión.
     *
     * @param password Contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
