package com.generation.caiv.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.responses.ApiResponse;


@Configuration
public class SwaggerConfig {
	
	// Configurações de informações pessoais da aplicação e seus desenvolvedores
	@Bean
	public OpenAPI springCaivOpenAPI() {
		
        return new OpenAPI()
				.info(new Info()
					.title("Projeto Integrador - CAIV")
					.description("Projeto Integrador - Central de Acesso a Informações aos Vulneráveis")
					.version("v0.0.1") // Versão da aplicação, atualizando cada vez que mudar
				.license(new License()
					.name("Generation Brasil")
					.url("https://brazil.generation.org/")) // Informações da empresa
				.contact(new Contact() // Informações de contato / repositório do projeto
					.name("Turma 50 - Grupo 2")
					.url("https://github.com/RafaelBalbino/ProjetoIntegrador")))
					//.email("")))
				.externalDocs(new ExternalDocumentation()
					.description("Resumo do Projeto")
					.url("https://github.com/RafaelBalbino/ProjetoIntegrador/blob/main/README.md"));
	}
	
	// Função pra traduzir as respostas 'http'
	@Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {

		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

				ApiResponses apiResponses = operation.getResponses();

				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído!"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso Não Autorizado!"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto Não Encontrado!"));
				apiResponses.addApiResponse("500", createApiResponse("Erro na Aplicação!"));

			}));
		};
	}
	
	// Cada vez que vem um status, ele cria uma resposta e envia ela para o cliente (No navegador)
		private ApiResponse createApiResponse(String message) {
			return new ApiResponse().description(message);
		}
}