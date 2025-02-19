package com.ejemplo.resenasPeliculas.config;

import com.ejemplo.resenasPeliculas.security.CustomUserDetailsService;
import com.ejemplo.resenasPeliculas.security.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

/**
 * Configuración de seguridad para la aplicación mediante Spring Security.
 * <p>
 * Define la autenticación, autorización y filtros de seguridad como JWT.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Proporciona el {@link AuthenticationManager} para gestionar la autenticación
     * de usuarios.
     *
     * @param authConfig Configuración de autenticación proporcionada por Spring
     *                   Security.
     * @return Una instancia de {@link AuthenticationManager}.
     * @throws Exception Si hay un error en la configuración del gestor de
     *                   autenticación.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Proporciona un {@link PasswordEncoder} que no codifica las contraseñas.
     * <p>
     * ⚠️ Solo se recomienda para entornos de desarrollo. En producción, usar una
     * estrategia segura como BCrypt.
     *
     * @return Un {@link PasswordEncoder} sin cifrado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * Configura la cadena de filtros de seguridad para manejar las solicitudes
     * HTTP.
     *
     * @param http La instancia de {@link HttpSecurity} proporcionada por Spring
     *             Security.
     * @return Un {@link SecurityFilterChain} configurado.
     * @throws Exception Si ocurre un error en la configuración de seguridad.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:8081")); // Permite el frontend
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF para permitir peticiones desde frontend
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/usuarios/registro", "/api/usuarios/login")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/peliculas/**", "/api/resenas/**").permitAll()
                        .anyRequest().authenticated()) // Protege otras rutas
                .userDetailsService(userDetailsService);

        // Agrega el filtro de autenticación JWT antes del filtro de autenticación de
        // usuario y contraseña
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
