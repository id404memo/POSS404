
package com.memo.poss404.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memo.poss404.PosViewModel

@Composable
fun AjustesScreen(vm: PosViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Ajustes", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Text("POSS404 v4 CLEAN - 5 secciones")
        Text("Productos: ${'$'}{vm.productos.size}")
        Text("Ventas: ${'$'}{vm.ventas.size}")
    }
}
