package com.example.stock.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenApi(): OpenAPI =  OpenAPI().info(
                Info()
                        .title("Azul E Rosa")
                        .version("0.1")
                        .description("Api Para fins de estudos Kotlin + Spring")
                        .contact(
                                Contact()
                                        .name("André França")
                                        .email("rsp.assistencia@gmail.com")
                        )

        )
    }

