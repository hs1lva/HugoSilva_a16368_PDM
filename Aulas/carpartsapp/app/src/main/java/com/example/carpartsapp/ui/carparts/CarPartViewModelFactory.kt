package com.example.carpartsapp.ui.carparts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carpartsapp.repositories.CarPartRepository

class CarPartViewModelFactory(private val carPartRepository: CarPartRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarPartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarPartViewModel(carPartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
