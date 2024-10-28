package com.example.stockslist

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.example.stockslist.models.Stock

@Composable
fun StocksView(modifier: Modifier = Modifier, searchQuery: String) {
    val viewModel: StocksViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current // Obter o contexto aqui

    if (uiState.isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Loading stocks...")
        }
    } else if (uiState.errorMessage.isNotEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = uiState.errorMessage)
        }
    } else {
        // Filtra os stocks com base na consulta de pesquisa
        val filteredStocks = uiState.stocks.filter { stock ->
            stock.symbol.contains(searchQuery, ignoreCase = true) ||
                    stock.name.contains(searchQuery, ignoreCase = true)
        }

        LazyColumn(modifier = modifier.fillMaxSize()) {
            itemsIndexed(filteredStocks) { _, stock ->
                StockRowView(stock = stock) { ticker ->
                    // Aqui abrir a URL do Yahoo Finance
                    val url = "https://finance.yahoo.com/quote/$ticker"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)) // Cria um intent
                    context.startActivity(intent) // Inicia a atividade
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.fetchStocks()
    }
}
