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
import androidx.room.Room
import com.example.carpartsapp.data.local.AppDatabase
import com.example.carpartsapp.repositories.CarRepository
import com.example.carpartsapp.repositories.CarPartRepository
import com.example.carpartsapp.ui.carlist.CarListView
import com.example.carpartsapp.ui.carparts.CarPartListView
import com.example.carpartsapp.ui.login.LoginView
import com.example.carpartsapp.ui.register.RegisterView
import com.example.carpartsapp.ui.theme.CarPartsAppTheme
import com.example.carpartsapp.ui.carparts.CarPartViewModel
import com.example.carpartsapp.ui.carparts.CarPartViewModelFactory
import com.example.carpartsapp.ui.carlist.CarViewModel
import com.example.carpartsapp.ui.carlist.CarViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "carparts_db"
        ).build()
    }

    private val carRepository by lazy {
        CarRepository(FirebaseFirestore.getInstance(), database)
    }

    private val carPartRepository by lazy {
        CarPartRepository(FirebaseFirestore.getInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CarPartsAppTheme {
                val navController = rememberNavController()
                val carViewModel: CarViewModel = viewModel(
                    factory = CarViewModelFactory(carRepository)
                )
                val carPartViewModel: CarPartViewModel = viewModel(
                    factory = CarPartViewModelFactory(carPartRepository)
                )

                Surface(color = MaterialTheme.colors.background) {
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
                                onShareCar = { carId, email ->
                                    carViewModel.shareCar(carId, email)
                                },
                                onLogout = {
                                    FirebaseAuth.getInstance().signOut()
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

