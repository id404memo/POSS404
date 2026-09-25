
package com.memo.poss404

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.memo.poss404.ui.screens.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PosApp() {
    var selectedTab by remember { mutableStateOf(0) }
    val vm: PosViewModel = viewModel()
    val tabs = listOf("POS", "Productos", "Clientes", "Ventas", "Ajustes")
    val icons = listOf(
        Icons.Filled.ShoppingCart,
        Icons.Filled.Home,
        Icons.Filled.Person,
        Icons.Filled.Receipt,
        Icons.Filled.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = title) },
                        label = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        androidx.compose.foundation.layout.Box(modifier = Modifier.padding(innerPadding)) {
            when(selectedTab) {
                0 -> CarritoScreen(vm) { selectedTab = 3 }
                1 -> ProductosScreen(vm)
                2 -> ClientesScreen(vm)
                3 -> VentasScreen(vm)
                4 -> AjustesScreen(vm)
            }
        }
    }
}
