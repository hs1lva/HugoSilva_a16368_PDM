package com.example.carpartsapp.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.example.carpartsapp.models.Car
import kotlinx.coroutines.tasks.await

class CarRepository(private val db: FirebaseFirestore) {

    fun listenToCars(onCarsChanged: (List<Car>) -> Unit): ListenerRegistration {
        return db.collection("cars")
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    Log.e("CarRepository", "Erro ao carregar carros", error)
                    return@addSnapshotListener
                }

                val carsList = snapshots?.documents?.mapNotNull { document ->
                    document.toObject(Car::class.java)?.apply { id = document.id }
                } ?: emptyList()

                Log.d("CarRepository", "Carros carregados: ${carsList.size}")
                onCarsChanged(carsList)
            }
    }

    suspend fun addCar(car: Car) {
        db.collection("cars").add(car).await()
    }

    suspend fun removeCar(carId: String) {
        db.collection("cars").document(carId).delete().await()
    }
}

