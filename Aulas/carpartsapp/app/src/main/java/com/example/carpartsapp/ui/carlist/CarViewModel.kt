package com.example.carpartsapp.ui.carlist

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carpartsapp.models.Car
import com.example.carpartsapp.repositories.CarRepository
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch

class CarViewModel(private val carRepository: CarRepository) : ViewModel() {
    val cars = mutableStateListOf<Car>()
    private var carsListenerRegistration: ListenerRegistration? = null

    init {
        listenToCars()
    }

    private fun listenToCars() {
        carsListenerRegistration = carRepository.listenToCars { updatedCars ->
            Log.d("CarViewModel", "Atualizando carros: ${updatedCars.size}")
            cars.clear()
            cars.addAll(updatedCars)
        }
    }

    fun addCar(model: String, year: Int) {
        viewModelScope.launch {
            try {
                val newCar = Car(id = "", model = model, year = year)
                carRepository.addCar(newCar)
            } catch (e: Exception) {
                // Handle error (ex. log or show error message)
            }
        }
    }

    fun removeCar(carId: String) {
        viewModelScope.launch {
            try {
                carRepository.removeCar(carId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        carsListenerRegistration?.remove() // Remove o listener ao limpar o ViewModel
    }
}


