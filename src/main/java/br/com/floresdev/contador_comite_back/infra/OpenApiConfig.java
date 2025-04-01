package br.com.floresdev.contador_comite_back.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                        .title("API do Contador de Receitas do Comitê de Formatura - CEMI do Cruzeiro 2025")
                        .description("API para gerenciamento financeiro e relatórios das atividades comerciais do comitê")
                        .version("1.0")
                        .contact(new Contact()
                                    .name("Miguel Fernando Flores Barreto")
                                    .email("miguelfernandoaurelius@gmail.com")));
    }

}
