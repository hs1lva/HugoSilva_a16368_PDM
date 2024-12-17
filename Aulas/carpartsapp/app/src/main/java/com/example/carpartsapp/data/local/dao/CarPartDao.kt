package com.example.carpartsapp.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.carpartsapp.data.local.entity.CarPartEntity

@Dao
interface CarPartDao {
    @Query("SELECT * FROM car_parts WHERE carId = :carId")
    fun getPartsForCar(carId: String): Flow<List<CarPartEntity>>

    @Query("SELECT * FROM car_parts WHERE carId = :carId AND purchased = :purchased")
    fun getPartsForCarByPurchaseStatus(carId: String, purchased: Boolean): Flow<List<CarPartEntity>>

    @Query("SELECT * FROM car_parts WHERE id = :partId")
    suspend fun getPartById(partId: String): CarPartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPart(part: CarPartEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParts(parts: List<CarPartEntity>)

    @Update
    suspend fun updatePart(part: CarPartEntity)

    @Delete
    suspend fun deletePart(part: CarPartEntity)

    @Query("DELETE FROM car_parts WHERE carId = :carId")
    suspend fun deleteAllPartsForCar(carId: String)

    @Query("UPDATE car_parts SET purchased = :purchased WHERE id = :partId")
    suspend fun updatePurchaseStatus(partId: String, purchased: Boolean)

    @Transaction
    @Query("""
        SELECT car_parts.* 
        FROM car_parts 
        INNER JOIN cars ON car_parts.carId = cars.id 
        WHERE cars.ownerId = :userId OR cars.id IN 
            (SELECT carId FROM car_shares WHERE sharedWithUserId = :userId)
    """)
    fun getAllAccessibleParts(userId: String): Flow<List<CarPartEntity>>

    @Query("SELECT COUNT(*) FROM car_parts WHERE carId = :carId")
    suspend fun getPartsCountForCar(carId: String): Int

    @Query("SELECT COUNT(*) FROM car_parts WHERE carId = :carId AND purchased = 1")
    suspend fun getPurchasedPartsCountForCar(carId: String): Int
}