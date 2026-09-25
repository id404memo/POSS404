
package com.memo.poss404.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memo.poss404.PosViewModel

@Composable
fun VentasScreen(vm: PosViewModel) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F7)).padding(16.dp)) {
        Text("Ventas", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1D1D1F), letterSpacing = (-0.5).sp)
        Spacer(Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(vm.ventas) { v ->
                Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier.fillMaxWidth().shadow(4.dp, RoundedCornerShape(16.dp))) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Venta #${v.id}", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF1D1D1F))
                            Box(modifier = Modifier.background(Color(0xFF34C759).copy(0.1f), RoundedCornerShape(20.dp)).padding(horizontal = 8.dp, vertical = 3.dp)) {
                                Text("Pagada", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34C759))
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                        Text("${v.cliente} \u2022 ${v.fecha} \u2022 Efectivo", fontSize = 12.sp, color = Color(0xFF86868B))
                        Spacer(Modifier.height(8.dp))
                        Text("$${String.format("%.2f", v.total)}", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1D1D1F))
                    }
                }
            }
        }
    }
}
