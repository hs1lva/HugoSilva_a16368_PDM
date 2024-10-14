package com.example.calculadorateste

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Chama a função CalculatorApp para mostrar a calculadora
            CalculatorApp()
        }
    }
}



