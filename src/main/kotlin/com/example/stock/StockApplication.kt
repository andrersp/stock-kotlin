package com.example.stock

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class StockApplication

fun main(args: Array<String>) {
	runApplication<StockApplication>(*args){
		setBannerMode(Banner.Mode.OFF)
	}
}
