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
fun CarritoScreen(vm: PosViewModel, onCheckout: () -> Unit = {}) {
    val total = vm.carrito.sumOf { it.precio }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("POS - Carrito", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        if(vm.carrito.isEmpty()){
            Text("Carrito vacio - agrega productos", modifier = Modifier.weight(1f))
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(vm.carrito) { p ->
                    ListItem(
                        headlineContent = { Text(p.nombre) },
                        supportingContent = { Text("Precio: $${p.precio}") }
                    )
                    Divider()
                }
            }
        }
        Divider()
        Text("Total: $${total}", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(vertical = 8.dp))
        Button(onClick = { vm.cobrar(); onCheckout() }, modifier = Modifier.fillMaxWidth(), enabled = vm.carrito.isNotEmpty()) {
            Text("COBRAR $${total}")
        }
    }
}
