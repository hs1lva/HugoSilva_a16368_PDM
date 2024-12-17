package com.example.carpartsapp.ui.carlist

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carpartsapp.models.Car
import com.example.carpartsapp.repositories.CarRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch

class CarViewModel(private val carRepository: CarRepository) : ViewModel() {
    val cars = mutableStateListOf<Car>()
    private var carsListenerRegistration: ListenerRegistration? = null
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    init {
        currentUserId?.let { userId ->
            viewModelScope.launch {
                carRepository.listenToCars(userId)
                    .catch { e ->
                        Log.e("CarViewModel", "Error loading cars", e)
                    }
                    .collect { updatedCars ->
                        cars.clear()
                        cars.addAll(updatedCars)
                    }
            }
        }
    }

    fun addCar(model: String, year: Int) {
        viewModelScope.launch {
            try {
                val newCar = Car(
                    model = model,
                    year = year,
                    ownerId = currentUserId ?: return@launch
                )
                carRepository.addCar(newCar)
            } catch (e: Exception) {
                Log.e("CarViewModel", "Error adding car", e)
            }
        }
    }

    fun shareCar(carId: String, targetEmail: String) {
        viewModelScope.launch {
            try {
                carRepository.shareCar(carId, targetEmail)
            } catch (e: Exception) {
                Log.e("CarViewModel", "Error sharing car", e)
            }
        }
    }

    fun removeCar(carId: String) {
        viewModelScope.launch {
            try {
                carRepository.removeCar(carId)
            } catch (e: Exception) {
                Log.e("CarViewModel", "Error removing car", e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        carsListenerRegistration?.remove()
    }
}