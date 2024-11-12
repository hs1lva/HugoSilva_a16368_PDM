package com.example.carpartsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carpartsapp.repositories.CarRepository
import com.example.carpartsapp.repositories.CarPartRepository
import com.example.carpartsapp.ui.CarListView
import com.example.carpartsapp.ui.CarPartListView
import com.example.carpartsapp.ui.LoginView
import com.example.carpartsapp.ui.RegisterView
import com.example.carpartsapp.ui.theme.CarPartsAppTheme
import com.example.carpartsapp.viewmodels.CarPartViewModel
import com.example.carpartsapp.viewmodels.CarPartViewModelFactory
import com.example.carpartsapp.viewmodels.CarViewModel
import com.example.carpartsapp.viewmodels.CarViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    private val carRepository = CarRepository(FirebaseFirestore.getInstance())
    private val carPartRepository = CarPartRepository(FirebaseFirestore.getInstance())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CarPartsAppTheme {
                val navController = rememberNavController()
                val carViewModel: CarViewModel = viewModel(factory = CarViewModelFactory(carRepository))
                val carPartViewModel: CarPartViewModel = viewModel(factory = CarPartViewModelFactory(carPartRepository))

                Surface(color = MaterialTheme.colors.background) {
                    // Verifica se há um utilizador autenticado
                    val currentUser = FirebaseAuth.getInstance().currentUser

                    NavHost(
                        navController = navController,
                        startDestination = if (currentUser == null) "login" else "car_list"
                    ) {
                        composable("login") {
                            LoginView(
                                navController = navController,
                                onLoginSuccess = { isAdmin ->
                                    navController.navigate("car_list") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable("register") {
                            RegisterView(navController = navController)
                        }

                        composable("car_list") {
                            CarListView(
                                cars = carViewModel.cars,
                                onCarSelected = { car ->
                                    navController.navigate("car_parts/${car.id}")
                                    carPartViewModel.listenToParts(car.id)
                                },
                                onAddCar = { model, year ->
                                    carViewModel.addCar(model, year)
                                },
                                onLogout = {
                                    FirebaseAuth.getInstance().signOut() // Termina a sessão
                                    navController.navigate("login") {
                                        popUpTo("car_list") { inclusive = true }
                                    }
                                },
                                navController = navController
                            )
                        }

                        composable("car_parts/{carId}") { backStackEntry ->
                            val carId = backStackEntry.arguments?.getString("carId") ?: return@composable
                            CarPartListView(
                                parts = carPartViewModel.parts,
                                onAddPart = { name, description ->
                                    carPartViewModel.addPart(carId, name, description)
                                },
                                onRemovePart = { part ->
                                    carPartViewModel.removePart(carId, part)
                                },
                                onTogglePurchased = { part ->
                                    carPartViewModel.togglePurchased(carId, part)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
