package br.com.fiap.tc.sistema.parquimetro.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Parquímetro - Grupo 23",
		version = "1.0.0",
		description = "Sistema de parquímetro desenvolvido para o Tech Challenge da segunda fase da Pós Tech da FIAP. O objetivo do parquímetro é monitorar os condutores em zonas de estacionamento reguladas, Iniciando o período de locação pelo sistema e emitindo um recibo sempre que o período de estacionamento é concluído."
))
public class ParquimetroApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParquimetroApiApplication.class, args);
	}

}
