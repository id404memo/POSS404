
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
fun ClientesScreen(vm: PosViewModel){
    var show by remember { mutableStateOf(false) }
    var edit by remember { mutableStateOf<ClientEntity?>(null) }
    var q by remember { mutableStateOf(vm.cliQuery) }

    Scaffold(topBar={ TopAppBar(title={Text("Clientes - Pág ${vm.cliPage+1} (33 c/u)")}) },
        floatingActionButton={ FloatingActionButton(onClick={edit=null; show=true}){ Icon(Icons.Default.PersonAdd,null) } }
    ){ pad ->
        Column(Modifier.padding(pad).padding(12.dp)){
            OutlinedTextField(q,{ q=it; vm.cliQuery=it; vm.refreshClientes() }, label={Text("Buscar cliente")}, modifier=Modifier.fillMaxWidth(), leadingIcon={Icon(Icons.Default.Search,null)})
            Row(Modifier.padding(top=8.dp)){ 
                OutlinedButton(onClick={ vm.prevCliPage() }, enabled=vm.cliPage>0){ Text("< 33") }
                Spacer(Modifier.width(8.dp))
                Button(onClick={ vm.nextCliPage() }){ Text("Siguiente 33 >") }
            }
            LazyColumn{
                items(vm.clientes){ c ->
                    Card(Modifier.fillMaxWidth().padding(vertical=4.dp)){
                        Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement=Arrangement.SpaceBetween){
                            Column{
                                Text(c.nombre, style=MaterialTheme.typography.titleMedium)
                                Text("${c.telefono} ${c.email}", style=MaterialTheme.typography.bodySmall)
                            }
                            Row{
                                IconButton(onClick={edit=c; show=true}){ Icon(Icons.Default.Edit,null) }
                                IconButton(onClick={vm.deleteClient(c)}){ Icon(Icons.Default.Delete,null) }
                            }
                        }
                    }
                }
            }
        }
    }
    if(show) ClientDialog(edit,{show=false},{ vm.saveClient(it){show=false} })
}

@Composable
fun ClientDialog(existing: ClientEntity?, onDismiss:()->Unit, onSave:(ClientEntity)->Unit){
    var nombre by remember { mutableStateOf(existing?.nombre ?: "") }
    var tel by remember { mutableStateOf(existing?.telefono ?: "") }
    var email by remember { mutableStateOf(existing?.email ?: "") }
    var dir by remember { mutableStateOf(existing?.direccion ?: "") }
    AlertDialog(onDismissRequest=onDismiss, title={Text(if(existing==null) "Nuevo Cliente" else "Editar Cliente")},
        text={
            Column(verticalArrangement=Arrangement.spacedBy(8.dp)){
                OutlinedTextField(nombre,{nombre=it}, label={Text("Nombre")}, modifier=Modifier.fillMaxWidth())
                OutlinedTextField(tel,{tel=it}, label={Text("Teléfono")}, modifier=Modifier.fillMaxWidth())
                OutlinedTextField(email,{email=it}, label={Text("Email")}, modifier=Modifier.fillMaxWidth())
                OutlinedTextField(dir,{dir=it}, label={Text("Dirección")}, modifier=Modifier.fillMaxWidth())
            }
        },
        confirmButton={ Button(onClick={ if(nombre.isNotBlank()) onSave(ClientEntity(id=existing?.id?:0, nombre=nombre, telefono=tel, email=email, direccion=dir)) }){ Text("Guardar") } },
        dismissButton={ TextButton(onClick=onDismiss){ Text("Cancelar") } }
    )
}
