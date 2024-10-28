package com.example.stockslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.stockslist.models.Stock

@Composable
fun StockRowView(
    modifier: Modifier = Modifier,
    stock: Stock,
    onStockClick: (String) -> Unit // Parametro para abrir Yahoo Finance
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onStockClick(stock.symbol) } // Define a ação do clique
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stock.symbol,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = stock.name,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Currency: ${stock.currency} | Exchange: ${stock.stockExchange}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}