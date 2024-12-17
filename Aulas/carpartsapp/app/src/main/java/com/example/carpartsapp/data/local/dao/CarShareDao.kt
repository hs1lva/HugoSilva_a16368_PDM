package com.example.carpartsapp.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.carpartsapp.data.local.entity.CarShareEntity

@Dao
interface CarShareDao {
    @Query("SELECT * FROM car_shares WHERE carId = :carId")
    fun getSharesForCar(carId: String): Flow<List<CarShareEntity>>

    @Query("SELECT * FROM car_shares WHERE sharedWithUserId = :userId")
    fun getSharesForUser(userId: String): Flow<List<CarShareEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarShare(carShare: CarShareEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarShares(carShares: List<CarShareEntity>)

    @Delete
    suspend fun deleteCarShare(carShare: CarShareEntity)

    @Query("DELETE FROM car_shares WHERE carId = :carId AND sharedWithUserId = :userId")
    suspend fun deleteCarShareByIds(carId: String, userId: String)

    @Query("DELETE FROM car_shares WHERE carId = :carId")
    suspend fun deleteAllSharesForCar(carId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM car_shares WHERE carId = :carId AND sharedWithUserId = :userId)")
    suspend fun isCarSharedWithUser(carId: String, userId: String): Boolean
}