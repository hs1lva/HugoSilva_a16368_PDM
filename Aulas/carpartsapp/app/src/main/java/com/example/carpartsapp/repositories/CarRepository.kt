package com.example.carpartsapp.repositories

import android.util.Log
import com.example.carpartsapp.data.local.AppDatabase
import com.example.carpartsapp.data.local.entity.toEntity
import com.example.carpartsapp.data.local.entity.toDomain
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.example.carpartsapp.models.Car
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class CarRepository(
    private val db: FirebaseFirestore,
    private val roomDb: AppDatabase
) {
    fun listenToCars(userId: String): Flow<List<Car>> = flow {
        try {
            // Query both owned and shared cars
            val ownedCars = db.collection("cars")
                .whereEqualTo("ownerId", userId)
                .get()
                .await()

            val sharedCars = db.collection("cars")
                .whereArrayContains("sharedWithUserIds", userId)
                .get()
                .await()

            val allCars = (ownedCars.documents + sharedCars.documents).mapNotNull { doc ->
                doc.toObject(Car::class.java)?.apply { id = doc.id }
            }

            // Cache in Room
            roomDb.carDao().insertAll(allCars.map { it.toEntity() })

            emit(allCars)
        } catch (e: Exception) {
            Log.e("CarRepository", "Error fetching cars", e)
            // Emit cached data from Room if available
            val cachedCars = roomDb.carDao().getAccessibleCars(userId)
                .collect { entities ->
                    emit(entities.map { it.toDomain() })
                }
        }
    }

    suspend fun shareCar(carId: String, targetUserId: String) {
        val carRef = db.collection("cars").document(carId)
        db.runTransaction { transaction ->
            val snapshot = transaction.get(carRef)
            val currentShares = snapshot.get("sharedWithUserIds") as? List<String> ?: emptyList()

            if (targetUserId !in currentShares) {
                transaction.update(carRef, "sharedWithUserIds", currentShares + targetUserId)
            }
        }.await()
    }

    suspend fun addCar(car: Car) {
        val docRef = db.collection("cars").add(car).await()
        car.id = docRef.id
        // Update local cache
        roomDb.carDao().insertCar(car.toEntity())
    }

    suspend fun removeCar(carId: String) {
        db.collection("cars").document(carId).delete().await()
        // Remove from local cache
        roomDb.carDao().deleteCarById(carId)
    }
}