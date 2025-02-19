package com.ejemplo.resenasPeliculas.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticación JWT para interceptar las solicitudes HTTP y validar
 * tokens JWT.
 * <p>
 * Extiende {@link OncePerRequestFilter}, lo que garantiza que se ejecute una
 * vez por solicitud.
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Método que intercepta las solicitudes HTTP, extrae y valida el token JWT si
     * está presente.
     *
     * @param request     La solicitud HTTP entrante.
     * @param response    La respuesta HTTP saliente.
     * @param filterChain La cadena de filtros de seguridad.
     * @throws ServletException Si ocurre un error en la ejecución del filtro.
     * @throws IOException      Si ocurre un error de entrada/salida durante la
     *                          ejecución del filtro.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Verifica si el encabezado de autorización contiene un token válido
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(token);
            } catch (Exception e) {
                logger.error("Error al extraer el username del token", e);
            }
        }

        // Si se extrajo un username y no hay autenticación
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continúa con la ejecución del siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }
}
