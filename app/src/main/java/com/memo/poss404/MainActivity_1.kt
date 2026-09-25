
package com.memo.poss404

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.memo.poss404.data.local.AppDatabase
import com.memo.poss404.data.repository.PosRepository
import com.memo.poss404.ui.navigation.Routes
import com.memo.poss404.ui.screens.*
import com.memo.poss404.ui.theme.POSS404Theme
import com.memo.poss404.ui.viewmodel.PosViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "poss404.db").fallbackToDestructiveMigration().build()
        val repo = PosRepository(db)
        val vm = PosViewModel(repo)

        // Seed demo data if empty
        CoroutineScope(Dispatchers.IO).launch {
            if(repo.countProductos()==0){
                listOf(
                    com.memo.poss404.data.local.entity.ProductEntity(codigo="AB001", nombre="Tornillo 1 pulgada", precio=2.5, stock=100, categoria="Ferretería"),
                    com.memo.poss404.data.local.entity.ProductEntity(codigo="AB002", nombre="Cuaderno Profesional", precio=45.0, stock=50, categoria="Papelería"),
                    com.memo.poss404.data.local.entity.ProductEntity(codigo="AB003", nombre="Zapato Escolar #26", precio=350.0, stock=10, categoria="Zapatería"),
                    com.memo.poss404.data.local.entity.ProductEntity(codigo="AB004", nombre="Pan Bolillo", precio=5.0, stock=200, categoria="Panadería"),
                    com.memo.poss404.data.local.entity.ProductEntity(codigo="AB005", nombre="Listón 5m", precio=15.0, stock=30, categoria="Mercería"),
                    com.memo.poss404.data.local.entity.ProductEntity(codigo="AB006", nombre="Bujía NGK", precio=120.0, stock=25, categoria="Refaccionaria")
                ).forEach { repo.upsertProducto(it) }
            }
        }

        setContent {
            POSS404Theme {
                var current by remember { mutableStateOf(Routes.PRODUCTOS) }
                val cartCount by vm.cartCount.collectAsState()
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(selected=current==Routes.PRODUCTOS, onClick={current=Routes.PRODUCTOS}, icon={Icon(Icons.Default.Inventory,null)}, label={Text("Productos")})
                            NavigationBarItem(selected=current==Routes.CLIENTES, onClick={current=Routes.CLIENTES}, icon={Icon(Icons.Default.People,null)}, label={Text("Clientes")})
                            NavigationBarItem(selected=current==Routes.CARRITO, onClick={current=Routes.CARRITO}, icon={BadgedBox(badge={ if(cartCount>0) Badge{Text("$cartCount")} }){ Icon(Icons.Default.ShoppingCart,null) }}, label={Text("Carrito")})
                            NavigationBarItem(selected=current==Routes.VENTAS, onClick={current=Routes.VENTAS}, icon={Icon(Icons.Default.ReceiptLong,null)}, label={Text("Ventas")})
                            NavigationBarItem(selected=current==Routes.AJUSTES, onClick={current=Routes.AJUSTES}, icon={Icon(Icons.Default.Settings,null)}, label={Text("Ajustes")})
                        }
                    }
                ){ pad ->
                    Box(Modifier.padding(pad)){
                        when(current){
                            Routes.PRODUCTOS -> ProductosScreen(vm, onAddToCart={ p -> vm.addToCart(p); current=Routes.CARRITO })
                            Routes.CLIENTES -> ClientesScreen(vm)
                            Routes.CARRITO -> CarritoScreen(vm, onCheckout={ current=Routes.VENTAS })
                            Routes.VENTAS -> VentasScreen(vm)
                            Routes.AJUSTES -> AjustesScreen(vm)
                        }
                    }
                }
            }
        }
    }
}
