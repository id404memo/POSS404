package com.memo.poss404.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memo.poss404.PosViewModel

@Composable
fun ProductosScreen(vm: PosViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Productos", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(vm.productos) { p ->
                ListItem(
                    headlineContent = { Text(p.nombre) },
                    supportingContent = { Text("Stock: ${p.stock} - $${p.precio}") },
                    trailingContent = {
                        Button(onClick = { vm.addToCart(p) }) { Text("Agregar") }
                    }
                )
                Divider()
            }
        }
    }
}
