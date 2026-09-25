
package com.memo.poss404.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memo.poss404.PosViewModel

@Composable
fun ProductosScreen(vm: PosViewModel) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F7)).padding(16.dp)) {
        // Search bar - mockup style
        Card(
            modifier = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Search, contentDescription = null, tint = Color(0xFF86868B))
                Spacer(Modifier.width(8.dp))
                TextField(
                    value = vm.searchQuery,
                    onValueChange = { vm.searchQuery = it },
                    placeholder = { Text("Buscar por nombre, SKU...", color = Color(0xFF86868B), fontSize = 14.sp) },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        // Stock badge - mockup style
        Row(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Stock total \u2022 ${vm.productos.sumOf { it.stock }}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF86868B))
        }
        Spacer(Modifier.height(14.dp))
        // Product list
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(vm.filteredProductos) { p ->
                Card(
                    modifier = Modifier.fillMaxWidth().shadow(6.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        // Image placeholder
                        Box(
                            modifier = Modifier.size(48.dp).background(Color(0xFFF5F5F7), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(p.nombre.first().toString(), fontWeight = FontWeight.Bold, color = Color(0xFF0071E3))
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(p.nombre, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = Color(0xFF1D1D1F), letterSpacing = (-0.2).sp)
                            Text("${p.sku} \u2022 ${p.categoria}", fontSize = 11.sp, color = Color(0xFF86868B))
                            Spacer(Modifier.height(2.dp))
                            Row {
                                Text("$${p.precio}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1D1D1F))
                                Spacer(Modifier.width(8.dp))
                                Text("Stock: ${p.stock}", fontSize = 11.sp, color = if(p.stock < 10) Color(0xFFFF3B30) else Color(0xFF34C759), modifier = Modifier.background(
                                    if(p.stock < 10) Color(0xFFFF3B30).copy(0.1f) else Color(0xFF34C759).copy(0.1f), RoundedCornerShape(6.dp)
                                ).padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }
                        Button(
                            onClick = { vm.addToCart(p) },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0071E3)),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                            modifier = Modifier.shadow(4.dp, RoundedCornerShape(10.dp))
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Agregar", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}
