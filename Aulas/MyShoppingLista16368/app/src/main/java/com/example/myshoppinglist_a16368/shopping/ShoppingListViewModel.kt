package com.example.myshoppinglist_a16368.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShoppingListViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _shoppingList = MutableStateFlow<List<ShoppingItem>>(emptyList())
    val shoppingList: StateFlow<List<ShoppingItem>> = _shoppingList

    init {
        listenToShoppingList() // Ouvir as mudanças na lista de compras
    }

    // Mudanças em tempo real na coleção "shoppingList"
    private fun listenToShoppingList() {
        db.collection("shoppingList")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    // Se houve erro...
                    exception.printStackTrace()
                    return@addSnapshotListener
                }

                // Se a resposta for bem-sucedida, atualiza a lista
                snapshot?.let { querySnapshot ->
                    _shoppingList.value = querySnapshot.documents.map { doc ->
                        ShoppingItem(
                            id = doc.id,
                            item = doc.getString("item") ?: "",
                            isBought = doc.getBoolean("isBought") ?: false
                        )
                    }
                }
            }
    }

    // Adicionar item
    fun addItem(item: String) {
        viewModelScope.launch {
            try {
                val shoppingItem = hashMapOf("item" to item, "isBought" to false)
                db.collection("shoppingList").add(shoppingItem).await()
            } catch (e: Exception) {
                e.printStackTrace() // Imprime o erro no log
            }
        }
    }

    // Alterar o estado de compra do item
    fun toggleItemBought(itemId: String, isBought: Boolean) {
        viewModelScope.launch {
            try {
                db.collection("shoppingList").document(itemId).update("isBought", isBought).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Remover um item
    fun removeItem(itemId: String) {
        viewModelScope.launch {
            try {
                db.collection("shoppingList").document(itemId).delete().await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
