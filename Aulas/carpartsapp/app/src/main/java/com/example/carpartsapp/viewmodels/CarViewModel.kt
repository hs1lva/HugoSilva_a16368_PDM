package com.example.carpartsapp.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carpartsapp.models.Car
import com.example.carpartsapp.repositories.CarRepository
import kotlinx.coroutines.launch

class CarViewModel(private val carRepository: CarRepository) : ViewModel() {
    val cars = mutableStateListOf<Car>()

    init {
        loadCars()
    }

    private fun loadCars() {
        viewModelScope.launch {
            cars.clear()
            cars.addAll(carRepository.getAllCars())
        }
    }

    fun addCar(model: String, year: Int) {
        viewModelScope.launch {
            val newCar = Car(id = "", model = model, year = year)
            carRepository.addCar(newCar)
            loadCars() // Recarregar carros após adicionar
        }
    }
}
