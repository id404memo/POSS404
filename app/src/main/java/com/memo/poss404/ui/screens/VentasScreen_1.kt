
package com.memo.poss404.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memo.poss404.ui.viewmodel.PosViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen(vm: PosViewModel){
    val fmt = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())
    Scaffold(topBar={ TopAppBar(title={Text("Ventas del Día - Pág ${vm.ventaPage+1}")}) }){ pad ->
        Column(Modifier.padding(pad).padding(12.dp)){
            Row{
                OutlinedButton(onClick={vm.prevVentaPage()}, enabled=vm.ventaPage>0){ Text("< 33") }
                Spacer(Modifier.width(8.dp))
                Button(onClick={vm.nextVentaPage()}){ Text("Siguiente 33 >") }
                Spacer(Modifier.width(8.dp))
                TextButton(onClick={vm.refreshVentas()}){ Text("Hoy") }
            }
            LazyColumn{
                items(vm.ventas){ v ->
                    Card(Modifier.fillMaxWidth().padding(vertical=4.dp), onClick={ vm.loadVentaDetalle(v) }){
                        Column(Modifier.padding(12.dp)){
                            Text("Venta #${v.id} - ${v.clienteNombre}", style=MaterialTheme.typography.titleMedium)
                            Text("${fmt.format(Date(v.fecha))} - ${v.itemsCount} prod. - Total $${"%.2f".format(v.total)}")
                        }
                    }
                }
            }
            vm.ventaDetalle?.let { (venta, items) ->
                AlertDialog(onDismissRequest={vm.ventaDetalle=null}, title={Text("Venta #${venta.id}")},
                    text={
                        Column{
                            Text("Cliente: ${venta.clienteNombre}")
                            Text("Fecha: ${fmt.format(Date(venta.fecha))}")
                            Text("Total: $${"%.2f".format(venta.total)}", style=MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(8.dp))
                            items.forEach { it -> Text("${it.cantidad}x ${it.nombre} @ $${it.precio}") }
                        }
                    },
                    confirmButton={ TextButton(onClick={vm.ventaDetalle=null}){ Text("Cerrar") } }
                )
            }
        }
    }
}
