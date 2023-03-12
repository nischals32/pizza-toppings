package com.example.pizza.toppings

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching

class ToppingsApplication

fun main(args: Array<String>) {
	runApplication<ToppingsApplication>(*args)
}
