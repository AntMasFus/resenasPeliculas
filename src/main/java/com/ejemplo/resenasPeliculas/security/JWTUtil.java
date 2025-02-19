package com.ejemplo.resenasPeliculas.security;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

/**
 * Utilidad para la generación y validación de tokens JWT en la aplicación.
 */
@Component
public class JWTUtil {

    /**
     * Clave secreta para la firma del token JWT.
     * Debe ser segura y almacenada adecuadamente en producción.
     */
    private final String jwtSecret = "mySuperSecretKeyForJWTmySuperSecretKeyForJWT";

    /**
     * Tiempo de expiración del token en milisegundos (24 horas).
     */
    private final long jwtExpirationMs = 86400000L;

    /**
     * Obtiene la clave de firma utilizada para firmar y validar los tokens JWT.
     *
     * @return Clave de firma utilizada para los tokens.
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Genera un token JWT basado en el nombre de usuario proporcionado.
     *
     * @param username Nombre de usuario para el cual se generará el token.
     * @return Token JWT generado.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el nombre de usuario de un token JWT.
     *
     * @param token Token JWT del cual se extraerá el nombre de usuario.
     * @return Nombre de usuario contenido en el token.
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Valida un token JWT verificando su integridad y expiración.
     *
     * @param token Token JWT a validar.
     * @return {@code true} si el token es válido, {@code false} si es inválido o ha
     *         expirado.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token inválido o expirado
        }
        return false;
    }
}
