package com.example.carpartsapp.ui.carparts

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carpartsapp.models.CarPart
import com.example.carpartsapp.repositories.CarPartRepository
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch

class CarPartViewModel(private val carPartRepository: CarPartRepository) : ViewModel() {
    val parts = mutableStateListOf<CarPart>()
    var errorMessage = mutableStateOf("") // Armazena mensagens de erro
    private var partsListenerRegistration: ListenerRegistration? = null

    // Listener para partes específicas de um carro
    fun listenToParts(carId: String) {
        partsListenerRegistration?.remove() // Remove o listener anterior, se houver
        partsListenerRegistration = carPartRepository.listenToPartsForCar(carId) { updatedParts ->
            parts.clear()
            parts.addAll(updatedParts)
            errorMessage.value = "" // Limpa qualquer mensagem de erro ao atualizar partes
        }
    }

    // Adiciona uma nova parte ao carro
    fun addPart(carId: String, name: String, description: String) {
        if (name.isBlank() || description.isBlank()) {
            errorMessage.value = "Name and description cannot be empty"
            return
        }

        viewModelScope.launch {
            try {
                val newPart = CarPart(id = "", name = name, description = description, purchased = false)
                carPartRepository.addPartToCar(carId, newPart)
                errorMessage.value = "" // Limpa mensagem de erro após adição bem-sucedida
            } catch (e: Exception) {
                errorMessage.value = "Failed to add part: ${e.message}"
            }
        }
    }

    // Remove uma parte específica do carro
    fun removePart(carId: String, part: CarPart) {
        viewModelScope.launch {
            part.id?.let { partId ->
                try {
                    carPartRepository.removePartFromCar(carId, partId)
                    parts.remove(part)
                    errorMessage.value = "" // Limpa mensagem de erro após remoção bem-sucedida
                } catch (e: Exception) {
                    errorMessage.value = "Failed to remove part: ${e.message}"
                }
            }
        }
    }

    // Marca a parte como comprada ou não
    fun togglePurchased(carId: String, part: CarPart) {
        viewModelScope.launch {
            part.id?.let { partId ->
                val updatedPurchasedStatus = !part.purchased
                try {
                    carPartRepository.markPartAsPurchased(carId, partId, updatedPurchasedStatus)
                    val index = parts.indexOf(part)
                    if (index >= 0) {
                        parts[index] = part.copy(purchased = updatedPurchasedStatus)
                    }
                    errorMessage.value = ""
                } catch (e: Exception) {
                    errorMessage.value = "Failed to update purchase status: ${e.message}"
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        partsListenerRegistration?.remove() // Remove o listener ao limpar o ViewModel
    }
}

