package com.example.carpartsapp.managers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserManager(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {

    // Função para registrar o usuário e definir o campo isAdmin no Firestore
    suspend fun registerUser(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception?) -> Unit) {
        try {
            // Cria o usuário com e-mail e senha
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: throw Exception("User ID not found")

            // Define o campo isAdmin para "teste@ipca.pt" como true, e false para outros usuários
            val isAdmin = email == "teste@ipca.pt"

            // Cria um documento para o usuário na coleção "users" no Firestore
            db.collection("users").document(userId).set(mapOf("isAdmin" to isAdmin)).await()

            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    // Função de login para verificar o campo isAdmin no Firestore
    suspend fun loginUser(email: String, password: String, onSuccess: (Boolean) -> Unit, onFailure: (Exception?) -> Unit) {
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: throw Exception("User ID not found")

            // Verifica o campo "isAdmin" no Firestore
            val snapshot = db.collection("users").document(userId).get().await()
            val isAdmin = snapshot.getBoolean("isAdmin") ?: false

            onSuccess(isAdmin)
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}
