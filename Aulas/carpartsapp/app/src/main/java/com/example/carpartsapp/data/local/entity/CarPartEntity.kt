package com.example.carpartsapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.carpartsapp.models.CarPart

@Entity(
    tableName = "car_parts",
    foreignKeys = [
        ForeignKey(
            entity = CarEntity::class,
            parentColumns = ["id"],
            childColumns = ["carId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("carId")
    ]
)
data class CarPartEntity(
    @PrimaryKey val id: String,
    val carId: String,
    val name: String,
    val description: String,
    val purchased: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

// Extension functions para converter entre Entity e Domain
fun CarPartEntity.toDomain() = CarPart(
    id = id,
    name = name,
    description = description,
    purchased = purchased
)

fun CarPart.toEntity(carId: String) = CarPartEntity(
    id = id,
    carId = carId,
    name = name,
    description = description,
    purchased = purchased
)