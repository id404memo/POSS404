
package com.memo.poss404.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
fun AjustesScreen(vm: PosViewModel) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F7)).padding(16.dp)) {
        Text("Ajustes", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1D1D1F))
        Spacer(Modifier.height(16.dp))
        Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier.fillMaxWidth().shadow(6.dp, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("POSS404 v6 MOCKUP EDITION", fontWeight = FontWeight.Bold, fontSize = 16.sp, letterSpacing = (-0.3).sp, color = Color(0xFF1D1D1F))
                Spacer(Modifier.height(8.dp))
                Text("Diseno basado en mockup React - Apple style", fontSize = 13.sp, color = Color(0xFF86868B))
                Spacer(Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Productos", fontSize = 13.sp, color = Color(0xFF86868B))
                    Text("${vm.productos.size}", fontWeight = FontWeight.Bold, color = Color(0xFF1D1D1F))
                }
                Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color.Black.copy(0.06f))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Ventas", fontSize = 13.sp, color = Color(0xFF86868B))
                    Text("${vm.ventas.size}", fontWeight = FontWeight.Bold, color = Color(0xFF1D1D1F))
                }
                Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color.Black.copy(0.06f))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Stock total", fontSize = 13.sp, color = Color(0xFF86868B))
                    Text("${vm.productos.sumOf { it.stock }}", fontWeight = FontWeight.Bold, color = Color(0xFF0071E3))
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Colores mockup", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(Modifier.size(32.dp).background(Color(0xFF0071E3), RoundedCornerShape(8.dp)))
                    Box(Modifier.size(32.dp).background(Color(0xFFF5F5F7), RoundedCornerShape(8.dp)))
                    Box(Modifier.size(32.dp).background(Color(0xFF1D1D1F), RoundedCornerShape(8.dp)))
                    Box(Modifier.size(32.dp).background(Color(0xFFFF9500), RoundedCornerShape(8.dp)))
                    Box(Modifier.size(32.dp).background(Color(0xFF34C759), RoundedCornerShape(8.dp)))
                    Box(Modifier.size(32.dp).background(Color(0xFFFF3B30), RoundedCornerShape(8.dp)))
                }
            }
        }
    }
}
