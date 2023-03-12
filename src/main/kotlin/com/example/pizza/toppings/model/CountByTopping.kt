package com.example.pizza.toppings.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("CountByTopping")
class CountByTopping (
    @Id
    val topping: String,
    val count: Long)
