
package com.memo.poss404.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memo.poss404.data.local.entity.ClientEntity
import com.memo.poss404.ui.viewmodel.PosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(vm: PosViewModel, onCheckout:()->Unit){
    val cart by vm.cart.collectAsState()
    val total by vm.cartTotal.collectAsState()
    var clienteSel by remember { mutableStateOf<ClientEntity?>(null) }
    var showCliente by remember { mutableStateOf(false) }

    Scaffold(topBar={ TopAppBar(title={Text("Carrito - ${cart.sumOf{it.qty}} artículos - $${"%.2f".format(total)}")}, actions={
        IconButton(onClick={vm.clearCart()}){ Icon(Icons.Default.DeleteSweep,null) }
    }) }){ pad ->
        Column(Modifier.padding(pad).padding(12.dp)){
            if(cart.isEmpty()){
                Box(Modifier.fillMaxSize(), contentAlignment=androidx.compose.ui.Alignment.Center){
                    Text("Carrito vacío. Ve a Productos y agrega productos.")
                }
            } else {
                Card(Modifier.fillMaxWidth()){ 
                    Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement=Arrangement.SpaceBetween){
                        Column{ Text(clienteSel?.nombre ?: "Cliente: Público General"); if(clienteSel!=null) Text(clienteSel!!.telefono, style=MaterialTheme.typography.bodySmall) }
                        OutlinedButton(onClick={showCliente=true}){ Text("Elegir Cliente") }
                    }
                }
                Spacer(Modifier.height(8.dp))
                LazyColumn(Modifier.weight(1f)){
                    items(cart){ line ->
                        Card(Modifier.fillMaxWidth().padding(vertical=4.dp)){
                            Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement=Arrangement.SpaceBetween){
                                Column(Modifier.weight(1f)){
                                    Text(line.product.nombre, style=MaterialTheme.typography.titleMedium)
                                    Text("$${line.product.precio} c/u - Código: ${line.product.codigo}", style=MaterialTheme.typography.bodySmall)
                                }
                                Row(verticalAlignment=androidx.compose.ui.Alignment.CenterVertically){
                                    IconButton(onClick={vm.decCart(line.product.id)}){ Icon(Icons.Default.Remove,null) }
                                    Text("${line.qty}")
                                    IconButton(onClick={vm.incCart(line.product.id)}){ Icon(Icons.Default.Add,null) }
                                    IconButton(onClick={vm.removeCart(line.product.id)}){ Icon(Icons.Default.Delete,null) }
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                Button(onClick={
                    vm.checkout(clienteSel){ id -> onCheckout() }
                }, modifier=Modifier.fillMaxWidth().height(56.dp)){
                    Icon(Icons.Default.PointOfSale,null); Spacer(Modifier.width(8.dp)); Text("COBRAR $${"%.2f".format(total)}", style=MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
    if(showCliente){
        AlertDialog(onDismissRequest={showCliente=false}, title={Text("Seleccionar Cliente")},
            text={
                LazyColumn{
                    item{ ListItem(headlineContent={Text("Público General")}, modifier=Modifier.fillMaxWidth(), trailingContent={ Button(onClick={ clienteSel=null; showCliente=false }){ Text("Usar") } }) }
                    items(vm.clientes){ c ->
                        ListItem(headlineContent={Text(c.nombre)}, supportingContent={Text(c.telefono)}, trailingContent={ Button(onClick={ clienteSel=c; showCliente=false }){ Text("Usar") } })
                    }
                }
            },
            confirmButton={ TextButton(onClick={showCliente=false}){ Text("Cerrar") } }
        )
    }
}
