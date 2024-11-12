package com.example.carpartsapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.carpartsapp.models.CarPart
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete


@Composable
fun CarPartListView(
    parts: List<CarPart>,
    onAddPart: (String, String) -> Unit,
    onRemovePart: (CarPart) -> Unit,
    onTogglePurchased: (CarPart) -> Unit
) {
    var partName by remember { mutableStateOf("") }
    var partDescription by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(parts) { part ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = part.name, style = MaterialTheme.typography.h6)
                        Text(text = part.description, style = MaterialTheme.typography.body1)
                        Checkbox(
                            checked = part.purchased,
                            onCheckedChange = { onTogglePurchased(part) }
                        )
                    }
                    IconButton(onClick = { onRemovePart(part) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove Part")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Inputs para adicionar uma nova peça
        Row {
            TextField(
                value = partName,
                onValueChange = { partName = it },
                label = { Text("Part Name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = partDescription,
                onValueChange = { partDescription = it },
                label = { Text("Description") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onAddPart(partName, partDescription)
                partName = ""
                partDescription = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Part")
        }
    }
}
