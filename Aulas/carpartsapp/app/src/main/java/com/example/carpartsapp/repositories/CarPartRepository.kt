package com.example.carpartsapp.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.example.carpartsapp.models.CarPart
import kotlinx.coroutines.tasks.await

class CarPartRepository(private val db: FirebaseFirestore) {

    fun listenToPartsForCar(carId: String, onPartsChanged: (List<CarPart>) -> Unit): ListenerRegistration {
        return db.collection("cars").document(carId).collection("parts")
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    // Log error if needed
                    return@addSnapshotListener
                }

                val partsList = snapshots?.documents?.mapNotNull { document ->
                    document.toObject(CarPart::class.java)?.apply { id = document.id }
                } ?: emptyList()

                onPartsChanged(partsList)
            }
    }

    suspend fun addPartToCar(carId: String, part: CarPart) {
        try {
            db.collection("cars").document(carId).collection("parts").add(part).await()
        } catch (e: Exception) {
            throw e // Repassa a exceção para o ViewModel tratar
        }
    }

    suspend fun removePartFromCar(carId: String, partId: String) {
        try {
            db.collection("cars").document(carId).collection("parts").document(partId).delete().await()
        } catch (e: Exception) {
            throw e // Repassa a exceção para o ViewModel tratar
        }
    }

    suspend fun markPartAsPurchased(carId: String, partId: String, purchased: Boolean) {
        try {
            db.collection("cars").document(carId).collection("parts").document(partId)
                .update("purchased", purchased).await()
        } catch (e: Exception) {
            throw e // Repassa a exceção para o ViewModel tratar
        }
    }
}


