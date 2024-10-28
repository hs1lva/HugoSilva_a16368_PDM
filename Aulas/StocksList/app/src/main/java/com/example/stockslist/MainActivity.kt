package com.example.stockslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.stockslist.ui.theme.StocksListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StocksListTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var searchQuery by remember { mutableStateOf("") } // Definição do estado para searchQuery

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stocks List") },
                actions = {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search by ticker") },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { /* Ação de navegar para a tela inicial */ }) {
                    Icon(Icons.Default.Home, contentDescription = "Home")
                }
                Spacer(Modifier.weight(1f)) // Para espaçar os ícones
                IconButton(onClick = { /* Ação de abrir configurações */ }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        }
    ) { innerPadding ->
        // Conteúdo principal aqui (ex: StocksView)
        StocksView(modifier = Modifier.padding(innerPadding), searchQuery = searchQuery)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StocksListTheme {
        MainScreen()
    }
}