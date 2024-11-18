package com.example.carpartsapp.ui.carlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carpartsapp.repositories.CarRepository

class CarViewModelFactory(private val carRepository: CarRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarViewModel(carRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
