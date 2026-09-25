
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
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("POS - Carrito", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(vm.carrito) { p ->
                ListItem(headlineContent = { Text(p.nombre) }, supportingContent = { Text("$${'$'}{p.precio}") })
                Divider()
            }
        }
        Text("Total: $${'$'}{vm.carrito.sumOf { it.precio }}", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Button(onClick = { vm.cobrar(); onCheckout() }, modifier = Modifier.fillMaxWidth()) {
            Text("COBRAR")
        }
    }
}
