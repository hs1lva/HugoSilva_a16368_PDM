package com.example.carpartsapp.data.local.dao

import androidx.room.*
import com.example.carpartsapp.data.local.entity.CarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    // Queries básicas
    @Query("SELECT * FROM cars")
    fun getAllCars(): Flow<List<CarEntity>>

    @Query("SELECT * FROM cars WHERE ownerId = :userId")
    fun getOwnedCars(userId: String): Flow<List<CarEntity>>

    @Query("SELECT * FROM cars WHERE id = :carId")
    suspend fun getCarById(carId: String): CarEntity?

    // Operações de inserção
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: CarEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cars: List<CarEntity>)

    // Operações de atualização e remoção
    @Update
    suspend fun updateCar(car: CarEntity)

    @Delete
    suspend fun deleteCar(car: CarEntity)

    @Query("DELETE FROM cars WHERE id = :carId")
    suspend fun deleteCarById(carId: String)

    @Query("DELETE FROM cars WHERE ownerId = :userId")
    suspend fun deleteAllUserCars(userId: String)

    @Query("DELETE FROM cars")
    suspend fun deleteAllCars()

    // Queries de busca
    @Query("""
        SELECT DISTINCT c.* FROM cars c
        LEFT JOIN car_shares cs ON c.id = cs.carId
        WHERE c.ownerId = :userId OR cs.sharedWithUserId = :userId
    """)
    fun getAccessibleCars(userId: String): Flow<List<CarEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM cars WHERE id = :carId AND ownerId = :userId)")
    suspend fun isCarOwnedByUser(carId: String, userId: String): Boolean

    @Query("""
        SELECT COUNT(DISTINCT c.id) FROM cars c
        LEFT JOIN car_shares cs ON c.id = cs.carId
        WHERE c.ownerId = :userId OR cs.sharedWithUserId = :userId
    """)
    suspend fun getAccessibleCarsCount(userId: String): Int

    @Query("""
        SELECT * FROM cars 
        WHERE model LIKE '%' || :searchQuery || '%' 
        OR CAST(year as TEXT) LIKE '%' || :searchQuery || '%'
    """)
    fun searchCars(searchQuery: String): Flow<List<CarEntity>>

    @Transaction
    suspend fun replaceAll(cars: List<CarEntity>) {
        deleteAllCars()
        insertAll(cars)
    }
}