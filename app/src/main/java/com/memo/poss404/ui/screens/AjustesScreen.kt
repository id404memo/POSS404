
package com.memo.poss404.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memo.poss404.data.local.entity.SettingsEntity
import com.memo.poss404.ui.viewmodel.PosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjustesScreen(vm: PosViewModel){
    var nombre by remember(vm.settings) { mutableStateOf(vm.settings.nombreNegocio) }
    var dir by remember(vm.settings) { mutableStateOf(vm.settings.direccion) }
    var footer by remember(vm.settings) { mutableStateOf(vm.settings.ticketFooter) }

    Scaffold(topBar={ TopAppBar(title={Text("Ajustes - POSS404")}) }){ pad ->
        Column(Modifier.padding(pad).padding(16.dp), verticalArrangement=Arrangement.spacedBy(12.dp)){
            Card(Modifier.fillMaxWidth()){
                Column(Modifier.padding(16.dp), verticalArrangement=Arrangement.spacedBy(8.dp)){
                    Text("Configuración del Negocio", style=MaterialTheme.typography.titleLarge)
                    OutlinedTextField(nombre,{nombre=it}, label={Text("Nombre del Negocio")}, modifier=Modifier.fillMaxWidth())
                    OutlinedTextField(dir,{dir=it}, label={Text("Dirección / Sucursal")}, modifier=Modifier.fillMaxWidth())
                    OutlinedTextField(footer,{footer=it}, label={Text("Mensaje del Ticket")}, modifier=Modifier.fillMaxWidth())
                    Button(onClick={ vm.saveSettings(SettingsEntity(nombreNegocio=nombre, direccion=dir, ticketFooter=footer)) }){
                        Text("Guardar Configuración")
                    }
                }
            }
            Card(Modifier.fillMaxWidth()){
                Column(Modifier.padding(16.dp)){
                    Text("Información", style=MaterialTheme.typography.titleMedium)
                    Text("App: POSS404 v1.0")
                    Text("Arquitectura: Kotlin + Compose + MVVM + Room")
                    Text("Para: Tiendas, Ferretería, Papelería, Zapatería, Panadería, Mercería, Refaccionaria")
                    Text("Paginación: 33 en 33")
                    Text("Negocio actual: ${vm.settings.nombreNegocio}")
                }
            }
        }
    }
}
