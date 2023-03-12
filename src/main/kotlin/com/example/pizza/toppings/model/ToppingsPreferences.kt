package com.example.pizza.toppings.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("ToppingsPreferences")
class ToppingsPreferences(
    @Id
    val email: String,
    val toppings: List<String>)