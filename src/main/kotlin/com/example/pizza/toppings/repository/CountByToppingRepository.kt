package com.example.pizza.toppings.repository

import com.example.pizza.toppings.model.CountByTopping
import org.springframework.stereotype.Repository
import org.springframework.data.repository.CrudRepository

@Repository
interface CountByToppingRepository: CrudRepository<CountByTopping, String> {}