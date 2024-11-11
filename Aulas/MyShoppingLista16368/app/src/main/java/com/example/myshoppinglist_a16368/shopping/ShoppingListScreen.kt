package com.example.myshoppinglist_a16368.shopping

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingListScreen(viewModel: ShoppingListViewModel, onBackToHome: () -> Unit) {
    var newItem by remember { mutableStateOf("") }
    val shoppingList by viewModel.shoppingList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // Campo para adicionar novo produto
        TextField(
            value = newItem,
            onValueChange = { newItem = it },
            label = { Text("Novo Produto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para adicionar novo produto
        Button(
            onClick = {
                viewModel.addItem(newItem)
                newItem = "" // Limpar o campo de entrada após adicionar
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Produto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Exibição da lista de compras com LazyColumn
        LazyColumn {
            items(shoppingList) { item ->
                ShoppingListItem(
                    item = item,
                    onToggleBought = { isBought -> viewModel.toggleItemBought(item.id, isBought) },
                    onRemove = { viewModel.removeItem(item.id) }
                )
            }
        }

        // Botão para voltar ao ecrã inicial
        Button(
            onClick = onBackToHome,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Retroceder")
        }
    }
}

@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onToggleBought: (Boolean) -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.item,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        // Checkbox para alternar o estado de "comprado"
        Checkbox(
            checked = item.isBought,
            onCheckedChange = { onToggleBought(!item.isBought) } // Inverte o estado
        )

        // Botão de remover item
        TextButton(
            onClick = onRemove,
            enabled = true // Para garantir que o botão está ativo
        ) {
            Text("Remover")
        }
    }
}
