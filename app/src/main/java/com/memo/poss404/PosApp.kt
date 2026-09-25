
package com.memo.poss404

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.memo.poss404.ui.screens.*

@Composable
fun PosApp() {
    var selectedTab by remember { mutableStateOf(0) }
    val vm: PosViewModel = viewModel()
    val tabs = listOf("POS", "Productos", "Clientes", "Ventas", "Ajustes")
    val icons = listOf(
        Icons.Filled.ShoppingCart,
        Icons.Filled.Store,
        Icons.Filled.Person,
        Icons.Filled.Receipt,
        Icons.Filled.Settings
    )

    Scaffold(
        containerColor = Color(0xFFF5F5F7),
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFF5F5F7).copy(alpha = 0.95f),
                tonalElevation = 0.dp,
                modifier = Modifier
                    .shadow(24.dp, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
            ) {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = title) },
                        label = { Text(title, style = MaterialTheme.typography.labelSmall) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF0071E3),
                            selectedTextColor = Color(0xFF0071E3),
                            indicatorColor = Color(0xFF0071E3).copy(alpha = 0.1f),
                            unselectedIconColor = Color(0xFF86868B),
                            unselectedTextColor = Color(0xFF86868B)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize().background(Color(0xFFF5F5F7))) {
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
