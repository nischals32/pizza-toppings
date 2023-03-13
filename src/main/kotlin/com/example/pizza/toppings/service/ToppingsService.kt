package com.example.pizza.toppings.service

import com.example.pizza.toppings.model.CountByTopping
import com.example.pizza.toppings.model.ToppingsPreferences
import com.example.pizza.toppings.repository.CountByToppingRepository
import com.example.pizza.toppings.repository.ToppingsPrefsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class ToppingsService {
    @Autowired
    private lateinit var toppingsRepository: ToppingsPrefsRepository

    @Autowired
    private lateinit var countsRepository: CountByToppingRepository

    fun create(toppingsPreferences: ToppingsPreferences): ToppingsPreferences {
        // Decrement counts for toppings if preferences already exist for this email
        val existingToppingsPrefs = toppingsRepository.findById(toppingsPreferences.email)
        if(existingToppingsPrefs.isPresent && !existingToppingsPrefs.isEmpty) {
            decrementCountsIfToppingsExist(existingToppingsPrefs.get().toppings)
        }

        // Save all counts based on the new toppings in preferences
        saveCountsForAllToppings(toppingsPreferences.toppings)

        // If we were doing async event-driven, then publish the event to stream or to a transactional outbox
        // instead of manipulating the other count repository like above. We can use Redis streams as a potential impl
        // https://medium.com/nerd-for-tech/event-driven-architecture-with-redis-streams-using-spring-boot-a81a1c9a4cde

        // save the new preferences
        return toppingsRepository.save(toppingsPreferences)
    }

    private fun decrementCountsIfToppingsExist(toppings: List<String>) {
        for(topping in toppings) {
            val counts = countsRepository.findById(topping)
            if(counts.isPresent && counts.get().count == 1L)
                countsRepository.delete(counts.get())
            else
                countsRepository.save(CountByTopping(topping, counts.get().count - 1))
        }
    }

    private fun saveCountsForAllToppings(toppings: List<String>) {
        for(topping in toppings) {
            val countForTopping = countsRepository.findById(topping)
            if (countForTopping.isPresent) {
                val newCount = CountByTopping(topping, countForTopping.get().count + 1)
                countsRepository.save(newCount)
            } else {
                countsRepository.save(CountByTopping(topping, 1))
            }
        }
    }

    fun findToppingsByEmail(email: String): Optional<ToppingsPreferences> {
        return toppingsRepository.findById(email)
    }

    fun findAllToppingsWithCounts(): List<CountByTopping> {
        return countsRepository.findAll() as List<CountByTopping>;
    }
}