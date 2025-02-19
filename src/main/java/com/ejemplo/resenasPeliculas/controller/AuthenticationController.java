package com.ejemplo.resenasPeliculas.controller;

import com.ejemplo.resenasPeliculas.dto.LoginRequestDto;
import com.ejemplo.resenasPeliculas.model.Usuario;
import com.ejemplo.resenasPeliculas.security.JWTUtil;
import com.ejemplo.resenasPeliculas.service.UsuarioService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gestionar la autenticación de los usuarios.
 * Proporciona un endpoint para el inicio de sesión y la generación de tokens
 * JWT.
 */
@RestController
@RequestMapping("/api/usuarios")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JWTUtil jwtUtil;

    /**
     * Autentica a un usuario y genera un token JWT en caso de éxito.
     *
     * @param loginRequest Objeto que contiene el nombre de usuario y la contraseña.
     * @return Si las credenciales son correctas, devuelve un token JWT; de lo
     *         contrario, devuelve un error 401.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));

            String token = jwtUtil.generateToken(loginRequest.getUsername());

            // Recuperar el usuario autenticado
            Usuario usuario = usuarioService.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            // Crear la respuesta con el token y datos del usuario
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", usuario);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}
