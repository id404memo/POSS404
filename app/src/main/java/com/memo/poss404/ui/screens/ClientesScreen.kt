
package com.memo.poss404.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
fun ClientesScreen(vm: PosViewModel) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F7)).padding(16.dp)) {
        Card(modifier = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(12.dp)), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Search, null, tint = Color(0xFF86868B))
                Spacer(Modifier.width(8.dp))
                TextField(value = "", onValueChange = {}, placeholder = { Text("Buscar clientes...", color = Color(0xFF86868B), fontSize = 14.sp) }, modifier = Modifier.weight(1f), colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent), singleLine = true)
            }
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(vm.clientes) { c ->
                Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(14.dp))) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(40.dp).background(Color(0xFF0071E3).copy(0.1f), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                            Text(c.nombre.first().toString(), fontWeight = FontWeight.Bold, color = Color(0xFF0071E3))
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(c.nombre, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF1D1D1F))
                            Text(c.telefono.ifBlank { "Sin telefono" }, fontSize = 11.sp, color = Color(0xFF86868B))
                        }
                    }
                }
            }
        }
    }
}
