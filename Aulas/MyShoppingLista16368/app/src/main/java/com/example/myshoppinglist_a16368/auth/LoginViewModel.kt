package com.example.myshoppinglist_a16368.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
                if (!task.isSuccessful) {
                    Log.e("LoginViewModel", "Erro no login", task.exception)
                }
            }
    }

    fun registerUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
                if (!task.isSuccessful) {
                    Log.e("LoginViewModel", "Erro no registo", task.exception)
                }
            }
    }
}
