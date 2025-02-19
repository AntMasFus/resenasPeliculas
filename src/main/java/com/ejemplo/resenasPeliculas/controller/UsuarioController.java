package com.ejemplo.resenasPeliculas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.ejemplo.resenasPeliculas.dto.UsuarioRegistrationDto;
import com.ejemplo.resenasPeliculas.model.Usuario;
import com.ejemplo.resenasPeliculas.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

/**
 * Controlador para gestionar los usuarios en la aplicación.
 * Proporciona endpoints para el registro de nuevos usuarios y obtención de
 * datos del usuario autenticado.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Registra un nuevo usuario en la aplicación.
     *
     * @param dto           Datos de registro del usuario.
     * @param bindingResult Resultado de la validación del formulario.
     * @return Respuesta con el usuario creado si el registro fue exitoso, o un
     *         mensaje de error en caso de fallo.
     */
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioRegistrationDto dto,
            BindingResult bindingResult) {
        // Si hay errores de validación, devolvemos un error 400
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        try {
            // Intentamos registrar al usuario
            Usuario usuario = usuarioService.registerUser(dto);
            return new ResponseEntity<>(usuario, HttpStatus.CREATED);
        } catch (Exception e) {
            // Si ocurre un error como usuario o email duplicado, retornamos un error 400
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Obtiene los datos del usuario autenticado.
     * 
     * @param authentication Información de autenticación del usuario.
     * @return Datos del usuario autenticado o un error si no está autenticado.
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }

        Usuario usuario = usuarioService.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return ResponseEntity.ok(usuario);
    }
}
