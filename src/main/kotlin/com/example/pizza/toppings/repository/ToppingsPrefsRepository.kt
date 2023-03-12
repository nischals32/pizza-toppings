package com.example.pizza.toppings.repository

import com.example.pizza.toppings.model.ToppingsPreferences
import org.springframework.stereotype.Repository
import org.springframework.data.repository.CrudRepository

@Repository
interface ToppingsPrefsRepository: CrudRepository<ToppingsPreferences, String> {}