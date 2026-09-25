
package com.memo.poss404.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun CarritoScreen(vm: PosViewModel, onCheckout: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F7)).padding(16.dp)) {
        // Header mockup style
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Carrito \u2022 ${vm.carrito.size} items", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1D1D1F), letterSpacing = (-0.5).sp)
            if(vm.carrito.isNotEmpty()){
                Box(modifier = Modifier.background(Color(0xFF0071E3).copy(0.1f), RoundedCornerShape(20.dp)).padding(horizontal = 10.dp, vertical = 4.dp)) {
                    Text(vm.selectedCliente.nombre.split(" ")[0], fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0071E3))
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        if(vm.carrito.isEmpty()){
            Card(modifier = Modifier.fillMaxWidth().weight(1f).shadow(6.dp, RoundedCornerShape(16.dp)), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("\uD83D\uDED2", fontSize = 32.sp)
                        Spacer(Modifier.height(8.dp))
                        Text("Carrito vacio", fontWeight = FontWeight.SemiBold, color = Color(0xFF1D1D1F))
                        Text("Agrega productos desde Productos", fontSize = 12.sp, color = Color(0xFF86868B))
                    }
                }
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(vm.carrito) { p ->
                    Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(14.dp))) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(p.nombre, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = Color(0xFF1D1D1F))
                                Text("1 x $${p.precio}", fontSize = 12.sp, color = Color(0xFF86868B))
                            }
                            Text("$${p.precio}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1D1D1F))
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        // Total card mockup style
        Card(modifier = Modifier.fillMaxWidth().shadow(8.dp, RoundedCornerShape(16.dp)), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Subtotal", fontSize = 13.sp, color = Color(0xFF86868B))
                    Text("$${String.format("%.2f", vm.subtotal)}", fontSize = 13.sp, color = Color(0xFF1D1D1F))
                }
                Spacer(Modifier.height(6.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("IVA 16%", fontSize = 13.sp, color = Color(0xFF86868B))
                    Text("$${String.format("%.2f", vm.subtotal * 0.16)}", fontSize = 13.sp, color = Color(0xFF1D1D1F))
                }
                Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color.Black.copy(0.06f))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Total", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1D1D1F), letterSpacing = (-0.3).sp)
                    Text("$${String.format("%.2f", vm.totalConIva)}", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = Color(0xFFFF9500), letterSpacing = (-0.8).sp)
                }
                Spacer(Modifier.height(4.dp))
                Text("Cliente: ${vm.selectedCliente.nombre}", fontSize = 11.sp, color = Color(0xFF86868B))
                Spacer(Modifier.height(14.dp))
                Button(
                    onClick = { vm.cobrar(); onCheckout() },
                    modifier = Modifier.fillMaxWidth().height(52.dp).shadow(8.dp, RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0071E3)),
                    enabled = vm.carrito.isNotEmpty()
                ) {
                    Text("Pagar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(Modifier.width(8.dp))
                    Text("$${String.format("%.2f", vm.totalConIva)}", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = Color.White.copy(0.8f))
                }
                if(vm.carrito.isNotEmpty()){
                    TextButton(onClick = { vm.carrito.clear() }, modifier = Modifier.fillMaxWidth()) {
                        Text("Vaciar carrito", color = Color(0xFFFF3B30).copy(0.8f), fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
