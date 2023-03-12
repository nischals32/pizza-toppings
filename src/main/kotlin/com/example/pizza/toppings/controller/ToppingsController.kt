package com.example.pizza.toppings.controller

import com.example.pizza.toppings.model.CountByTopping
import com.example.pizza.toppings.model.ToppingsPreferences
import com.example.pizza.toppings.service.ToppingsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.net.URISyntaxException
import java.util.*


@RestController
@RequestMapping("/toppings")
class ToppingsController {
    @Autowired
    lateinit var toppingService: ToppingsService

    @Value("\${my.email}")
    lateinit var myEmail: String

    @GetMapping("/{email}")
    fun read(@PathVariable("email") email: String): ResponseEntity<ToppingsPreferences>? {
        val foundToppings: Optional<ToppingsPreferences> = toppingService.findToppingsByEmail(email)
        return if (foundToppings.isPresent)
            ResponseEntity.ok().body(foundToppings.get())
        else
            ResponseEntity.notFound().build()
    }

    @GetMapping("/my-email")
    fun readMyToppingsPrefs(): ResponseEntity<ToppingsPreferences>? {
        return read(myEmail)
    }

    @GetMapping("/all")
    fun read(): List<CountByTopping> {
        return toppingService.findAllToppingsWithCounts()
    }

    @PostMapping("/")
    @Throws(URISyntaxException::class)
    fun create(@RequestBody account: ToppingsPreferences): ResponseEntity<ToppingsPreferences>? {
        val createdToppingsPreferences: ToppingsPreferences = toppingService.create(account)
        return run {
            val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdToppingsPreferences.email)
                .toUri()
            ResponseEntity.created(uri)
                .body(createdToppingsPreferences)
        }
    }

}