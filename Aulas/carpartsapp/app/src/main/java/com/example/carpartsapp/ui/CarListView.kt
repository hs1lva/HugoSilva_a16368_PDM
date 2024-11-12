package com.example.carpartsapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carpartsapp.models.Car

@Composable
fun CarListView(
    cars: List<Car>,
    onCarSelected: (Car) -> Unit,
    onAddCar: (String, Int) -> Unit,
    onLogout: () -> Unit,
    navController: NavController
) {
    var model by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var isInputValid by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // Lista de carros existente
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cars) { car ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onCarSelected(car) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = car.model, style = MaterialTheme.typography.h6)
                        Text(text = "Year: ${car.year}", style = MaterialTheme.typography.body1)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campos de input para adicionar um novo carro
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            TextField(
                value = model,
                onValueChange = {
                    model = it
                    isInputValid = it.isNotBlank()
                    errorMessage = if (isInputValid) "" else "Model cannot be empty"
                },
                label = { Text("Car Model") },
                modifier = Modifier.weight(1f),
                isError = !isInputValid
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = year,
                onValueChange = {
                    year = it
                    val yearInt = it.toIntOrNull()
                    isInputValid = yearInt != null && yearInt > 1885
                    errorMessage = if (isInputValid) "" else "Enter a valid year"
                },
                label = { Text("Year") },
                modifier = Modifier.weight(1f),
                isError = !isInputValid
            )
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colors.error, style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                val yearInt = year.toIntOrNull()
                if (yearInt != null && model.isNotBlank()) {
                    onAddCar(model, yearInt)
                    model = ""
                    year = ""
                    isInputValid = true
                    errorMessage = ""
                } else {
                    isInputValid = false
                    errorMessage = "Model and year are required"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isInputValid && model.isNotBlank() && year.isNotEmpty()
        ) {
            Text("Add Car")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão de logout
        Button(
            onClick = { onLogout() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}

