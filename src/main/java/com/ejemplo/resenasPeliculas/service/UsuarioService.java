package com.ejemplo.resenasPeliculas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplo.resenasPeliculas.dto.UsuarioRegistrationDto;
import com.ejemplo.resenasPeliculas.model.Usuario;
import com.ejemplo.resenasPeliculas.repository.UsuarioRepository;

/**
 * Servicio que maneja la lógica de negocio relacionada con los usuarios.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     */
    public Usuario registerUser(UsuarioRegistrationDto dto) throws Exception {
        // Verificar que el nombre de usuario no exista
        if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new Exception("El nombre de usuario ya existe");
        }
        // Verificar que el email no exista
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new Exception("El email ya existe");
        }

        // Creamos un nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());

        // Guardamos y retornamos el usuario registrado
        return usuarioRepository.save(usuario);
    }
}
