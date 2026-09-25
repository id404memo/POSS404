
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
fun VentasScreen(vm: PosViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Ventas", style = MaterialTheme.typography.headlineMedium)
        LazyColumn {
            items(vm.ventas) { v ->
                ListItem(headlineContent = { Text("Venta #${'$'}{v.id}") }, supportingContent = { Text("$${'$'}{v.total} - ${'$'}{v.fecha}") })
                Divider()
            }
        }
    }
}
