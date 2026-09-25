
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
import com.memo.poss404.data.local.entity.ProductEntity
import com.memo.poss404.ui.viewmodel.PosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(vm: PosViewModel, onAddToCart: (ProductEntity)->Unit){
    var showDialog by remember { mutableStateOf(false) }
    var edit by remember { mutableStateOf<ProductEntity?>(null) }
    var query by remember { mutableStateOf(vm.prodQuery) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Productos - \uD83D\uDCE6 ${vm.prodTotal} - Pág ${vm.prodPage+1}") }) },
        floatingActionButton = { FloatingActionButton(onClick = { edit=null; showDialog=true }){ Icon(Icons.Default.Add, null) } }
    ){ pad ->
        Column(Modifier.padding(pad).padding(12.dp)){
            OutlinedTextField(value=query, onValueChange={ query=it; vm.prodQuery=it; vm.refreshProductos() }, label={Text("Buscar por nombre o código")}, leadingIcon={Icon(Icons.Default.Search,null)}, modifier=Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            Row{ 
                OutlinedButton(onClick={ vm.prevProdPage() }, enabled=vm.prodPage>0){ Text("< 33") }
                Spacer(Modifier.width(8.dp))
                Button(onClick={ vm.nextProdPage() }){ Text("Siguiente 33 >") }
                Spacer(Modifier.width(8.dp))
                TextButton(onClick={ vm.refreshProductos() }){ Text("Actualizar") }
            }
            LazyColumn{
                items(vm.productos){ p ->
                    Card(Modifier.fillMaxWidth().padding(vertical=4.dp), elevation=CardDefaults.cardElevation(2.dp)){
                        Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement=Arrangement.SpaceBetween){
                            Column(Modifier.weight(1f)){
                                Text(p.nombre, style=MaterialTheme.typography.titleMedium)
                                Text("Código: ${p.codigo} | ${p.categoria} | Stock:${p.stock}", style=MaterialTheme.typography.bodySmall)
                                Text("$${p.precio}", color=MaterialTheme.colorScheme.primary, style=MaterialTheme.typography.titleMedium)
                            }
                            Column{
                                IconButton(onClick={ edit=p; showDialog=true }){ Icon(Icons.Default.Edit,null) }
                                IconButton(onClick={ vm.deleteProduct(p) }){ Icon(Icons.Default.Delete,null) }
                                Button(onClick={ onAddToCart(p) }, enabled=p.stock>0){ Icon(Icons.Default.ShoppingCart,null); Spacer(Modifier.width(4.dp)); Text("Agregar") }
                            }
                        }
                    }
                }
            }
        }
    }
    if(showDialog) ProductDialog(edit, onDismiss={showDialog=false}, onSave={ vm.saveProduct(it){ showDialog=false } })
}

@Composable
fun ProductDialog(existing: ProductEntity?, onDismiss:()->Unit, onSave:(ProductEntity)->Unit){
    var codigo by remember { mutableStateOf(existing?.codigo ?: "") }
    var nombre by remember { mutableStateOf(existing?.nombre ?: "") }
    var precio by remember { mutableStateOf(existing?.precio?.toString() ?: "") }
    var stock by remember { mutableStateOf(existing?.stock?.toString() ?: "") }
    var cat by remember { mutableStateOf(existing?.categoria ?: "General") }
    AlertDialog(onDismissRequest=onDismiss, title={Text(if(existing==null) "Nuevo Producto" else "Editar Producto")},
        text={
            Column(verticalArrangement=Arrangement.spacedBy(8.dp)){
                OutlinedTextField(codigo,{codigo=it}, label={Text("Código")}, modifier=Modifier.fillMaxWidth())
                OutlinedTextField(nombre,{nombre=it}, label={Text("Nombre")}, modifier=Modifier.fillMaxWidth())
                OutlinedTextField(precio,{precio=it}, label={Text("Precio")}, modifier=Modifier.fillMaxWidth())
                OutlinedTextField(stock,{stock=it}, label={Text("Stock")}, modifier=Modifier.fillMaxWidth())
                OutlinedTextField(cat,{cat=it}, label={Text("Categoría (Tienda,Ferretería,Zapatería...)")}, modifier=Modifier.fillMaxWidth())
            }
        },
        confirmButton={ Button(onClick={
            if(nombre.isNotBlank() && precio.toDoubleOrNull()!=null){
                onSave(ProductEntity(id=existing?.id ?: 0, codigo=codigo.ifBlank{"P${System.currentTimeMillis()}"}, nombre=nombre, precio=precio.toDouble(), stock=stock.toIntOrNull() ?: 0, categoria=cat))
            }
        }){ Text("Guardar") } },
        dismissButton={ TextButton(onClick=onDismiss){ Text("Cancelar") } }
    )
}
