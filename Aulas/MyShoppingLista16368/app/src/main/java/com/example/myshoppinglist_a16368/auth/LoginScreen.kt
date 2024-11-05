package com.example.myshoppinglist_a16368.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, loginViewModel: LoginViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoginMode by remember { mutableStateOf(true) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isLoginMode) {
                    loginViewModel.loginUser(email, password) { success ->
                        if (success) onLoginSuccess() else Toast.makeText(context, "Falha no login", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    loginViewModel.registerUser(email, password) { success ->
                        if (success) onLoginSuccess() else Toast.makeText(context, "Falha no registo", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoginMode) "Login" else "Registar")
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = { isLoginMode = !isLoginMode }) {
            Text(if (isLoginMode) "Criar nova conta" else "Já tem conta? Login")
        }
    }
}