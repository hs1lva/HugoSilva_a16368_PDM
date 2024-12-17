package com.example.carpartsapp.models

data class Car(
    var id: String = "",
    var model: String = "",
    var year: Int = 0,
    var ownerId: String = "",
    var sharedWithUserIds: List<String> = emptyList()
)

