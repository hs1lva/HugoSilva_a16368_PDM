package com.example.carpartsapp.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.example.carpartsapp.models.CarPart
import kotlinx.coroutines.tasks.await

class CarPartRepository(private val db: FirebaseFirestore) {

    suspend fun getPartsForCar(carId: String): List<CarPart> {
        return db.collection("cars").document(carId).collection("parts").get().await().documents.mapNotNull { document ->
            document.toObject(CarPart::class.java)?.apply { id = document.id }
        }
    }

    suspend fun addPartToCar(carId: String, part: CarPart) {
        db.collection("cars").document(carId).collection("parts").add(part).await()
    }

    suspend fun removePartFromCar(carId: String, partId: String) {
        db.collection("cars").document(carId).collection("parts").document(partId).delete().await()
    }

    suspend fun markPartAsPurchased(carId: String, partId: String, purchased: Boolean) {
        db.collection("cars").document(carId).collection("parts").document(partId)
            .update("purchased", purchased).await()
    }
}

