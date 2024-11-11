package com.example.myshoppinglist_a16368

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myshoppinglist_a16368.auth.LoginScreen
import com.example.myshoppinglist_a16368.auth.LoginViewModel
import com.example.myshoppinglist_a16368.home.HomeScreen
import com.example.myshoppinglist_a16368.shopping.ShoppingListScreen
import com.example.myshoppinglist_a16368.ui.theme.MyShoppingListTheme
import com.example.myshoppinglist_a16368.shopping.WorkScreen
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyShoppingListTheme {
                // Estado para verificar se o utilizador está logado
                var isUserLoggedIn by remember { mutableStateOf(auth.currentUser != null) }
                val loginViewModel: LoginViewModel = viewModel()

                // Se o utilizador estiver logado, mostra o ecrã de seleção
                if (isUserLoggedIn) {
                    MainScreen(onLogout = { isUserLoggedIn = false }) // Adicionando opção de logout
                } else {
                    // Se não estiver logado, mostra o ecrã de login
                    LoginScreen(
                        onLoginSuccess = { isUserLoggedIn = true },
                        loginViewModel = loginViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(onLogout: () -> Unit) {
    var selectedOption by remember { mutableStateOf("") }

    when (selectedOption) {
        "" -> HomeScreen(
            onSelect = { option -> selectedOption = option },
            onLogout = onLogout
        )
        "Casa" -> ShoppingListScreen(
            viewModel = viewModel(),
            onBackToHome = { selectedOption = "" } // Passar a função de voltar para Home
        )
        "Trabalho" -> WorkScreen(
            onBackToHome = { selectedOption = "" } // Passar a função de voltar para Home
        )
    }
}
