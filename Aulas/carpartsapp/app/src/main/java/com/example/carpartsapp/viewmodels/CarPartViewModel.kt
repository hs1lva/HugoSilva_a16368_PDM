package com.example.carpartsapp.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carpartsapp.models.CarPart
import com.example.carpartsapp.repositories.CarPartRepository
import kotlinx.coroutines.launch

class CarPartViewModel(private val carPartRepository: CarPartRepository) : ViewModel() {
    val parts = mutableStateListOf<CarPart>()

    fun loadParts(carId: String) {
        viewModelScope.launch {
            parts.clear()
            parts.addAll(carPartRepository.getPartsForCar(carId))
        }
    }

    fun addPart(carId: String, name: String, description: String) {
        viewModelScope.launch {
            val newPart = CarPart(id = "", name = name, description = description, purchased = false)
            carPartRepository.addPartToCar(carId, newPart)
            loadParts(carId) // Recarregar peças após adicionar
        }
    }

    fun removePart(carId: String, part: CarPart) {
        viewModelScope.launch {
            part.id?.let { partId ->
                carPartRepository.removePartFromCar(carId, partId)
                parts.remove(part)
            }
        }
    }

    fun togglePurchased(carId: String, part: CarPart) {
        viewModelScope.launch {
            part.id?.let { partId ->
                val updatedPurchasedStatus = !part.purchased
                carPartRepository.markPartAsPurchased(carId, partId, updatedPurchasedStatus)
                val index = parts.indexOf(part)
                if (index >= 0) {
                    parts[index] = part.copy(purchased = updatedPurchasedStatus)
                }
            }
        }
    }
}

