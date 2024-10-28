package com.example.stockslist.models

data class Stock(
    val symbol: String,
    val name: String,
    val currency: String,
    val stockExchange: String,
    val exchangeShortName: String
)