package com.example.carpartsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.carpartsapp.models.Car

@Entity(tableName = "cars")
data class CarEntity(
    @PrimaryKey val id: String,
    val model: String,
    val year: Int,
    val ownerId: String,
    val lastUpdated: Long = System.currentTimeMillis()
)

// Funções de extensão para conversão
fun Car.toEntity() = CarEntity(
    id = this.id,
    model = this.model,
    year = this.year,
    ownerId = this.ownerId
)

fun CarEntity.toDomain() = Car(
    id = this.id,
    model = this.model,
    year = this.year,
    ownerId = this.ownerId
)