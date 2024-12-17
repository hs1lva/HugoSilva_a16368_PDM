package com.example.carpartsapp.models

data class CarPart(
    var id: String = "",
    val name: String = "",
    val description: String = "",
    val purchased: Boolean = false,
    // Campos adicionais
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

