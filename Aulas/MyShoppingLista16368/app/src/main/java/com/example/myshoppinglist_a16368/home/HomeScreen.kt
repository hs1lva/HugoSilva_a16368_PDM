package com.example.myshoppinglist_a16368.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onSelect: (String) -> Unit, onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Escolha uma opção", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onSelect("Casa") }, modifier = Modifier.fillMaxWidth()) {
            Text("Casa")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onSelect("Trabalho") }, modifier = Modifier.fillMaxWidth()) {
            Text("Trabalho")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para logout
        Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
            Text("Logout")
        }
    }
}
