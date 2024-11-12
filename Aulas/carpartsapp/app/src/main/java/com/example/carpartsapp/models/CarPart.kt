package com.example.carpartsapp.models

data class CarPart(
    var id: String = "",
    val name: String,
    val description: String,
    val purchased: Boolean = false
)
