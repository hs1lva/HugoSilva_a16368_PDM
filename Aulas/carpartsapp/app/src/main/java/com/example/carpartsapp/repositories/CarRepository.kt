package com.example.carpartsapp.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.example.carpartsapp.models.Car
import kotlinx.coroutines.tasks.await

class CarRepository(private val db: FirebaseFirestore) {

    suspend fun getAllCars(): List<Car> {
        return db.collection("cars").get().await().documents.mapNotNull { document ->
            document.toObject(Car::class.java)?.apply { id = document.id }
        }
    }

    suspend fun addCar(car: Car) {
        db.collection("cars").add(car).await()
    }

    suspend fun removeCar(carId: String) {
        db.collection("cars").document(carId).delete().await()
    }
}
