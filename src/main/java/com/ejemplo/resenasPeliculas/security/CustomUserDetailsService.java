package com.ejemplo.resenasPeliculas.security;

import com.ejemplo.resenasPeliculas.model.Usuario;
import com.ejemplo.resenasPeliculas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Servicio de autenticación para cargar los detalles del usuario desde la base
 * de datos.
 * Implementa la interfaz {@link UserDetailsService} de Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Carga los detalles del usuario por su nombre de usuario.
     * <p>
     * Este método es utilizado por Spring Security para autenticar a los usuarios.
     *
     * @param username El nombre de usuario del usuario a buscar.
     * @return Un objeto {@link UserDetails} que contiene la información del usuario
     *         autenticado.
     * @throws UsernameNotFoundException Si el usuario no es encontrado en la base
     *                                   de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                Collections.emptyList());
    }
}
