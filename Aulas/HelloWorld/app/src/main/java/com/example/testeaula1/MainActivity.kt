package com.example.testeaula1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.testeaula1.ui.theme.TesteAula1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // funcao responsanvel por criar a tela
        enableEdgeToEdge() // funcao responsanvel por deixar a tela cheia
        setContent {
            TesteAula1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greet( // funcao responsanvel por colocar o texto na tela
                        modifier = Modifier.padding(innerPadding) // funcao responsanvel por deixar o texto centralizado
                    )
                }
            }
        }
    }
}