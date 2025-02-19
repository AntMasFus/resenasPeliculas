package com.ejemplo.resenasPeliculas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Clase principal de la aplicación de Reseñas de Películas.
 * <p>
 * Esta clase inicializa la aplicación Spring Boot y la ejecuta.
 */
@SpringBootApplication
public class ResenasPeliculasApplication {

	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 *
	 * @param args Argumentos de línea de comandos pasados a la aplicación.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ResenasPeliculasApplication.class, args);
	}
}
