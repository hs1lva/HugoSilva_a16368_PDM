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
        fetchShoppingList() // Carrega a lista ao iniciar o ViewModel
    }

    // Adicionar item
    fun addItem(item: String) {
        viewModelScope.launch {
            try {
                val shoppingItem = hashMapOf("item" to item, "isBought" to false)
                db.collection("shoppingList").add(shoppingItem).await()
                fetchShoppingList() // Atualiza a lista após adicionar o item
            } catch (e: Exception) {
                e.printStackTrace() // Imprime o erro no log
            }
        }
    }

    // Alternar o estado de compra do item
    fun toggleItemBought(itemId: String, isBought: Boolean) {
        viewModelScope.launch {
            try {
                db.collection("shoppingList").document(itemId).update("isBought", isBought).await()
                fetchShoppingList() // Atualiza a lista após a mudança de estado
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
                fetchShoppingList() // Atualiza a lista após a remoção
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Procurar todos os itens na lista de compras
    fun fetchShoppingList() {
        viewModelScope.launch {
            try {
                val querySnapshot = db.collection("shoppingList").get().await()
                _shoppingList.value = querySnapshot.documents.map { doc ->
                    ShoppingItem(
                        id = doc.id,
                        item = doc.getString("item") ?: "",
                        isBought = doc.getBoolean("isBought") ?: false
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}