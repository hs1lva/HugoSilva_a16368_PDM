package com.example.carpartsapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "car_shares",
    foreignKeys = [
        ForeignKey(
            entity = CarEntity::class,
            parentColumns = ["id"],
            childColumns = ["carId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("carId"),
        Index("sharedWithUserId")
    ]
)
data class CarShareEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val carId: String,
    val sharedWithUserId: String
)