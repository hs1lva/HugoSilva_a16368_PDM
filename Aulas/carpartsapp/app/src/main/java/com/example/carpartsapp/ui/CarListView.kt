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
    navController: NavController
) {
    var model by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cars) { car ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
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

        // Inputs para adicionar um novo carro
        Row {
            TextField(
                value = model,
                onValueChange = { model = it },
                label = { Text("Car Model") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Year") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val yearInt = year.toIntOrNull()
                if (yearInt != null) {
                    onAddCar(model, yearInt)
                    model = ""
                    year = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Car")
        }
    }
}
