
package com.memo.poss404.ui.viewmodel
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memo.poss404.data.local.entity.*
import com.memo.poss404.data.repository.PosRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CartLine(val product: ProductEntity, var qty: Int)

class PosViewModel(private val repo: PosRepository): ViewModel() {
    // Productos
    var productos by mutableStateOf<List<ProductEntity>>(emptyList())
    var prodPage by mutableStateOf(0)
    var prodQuery by mutableStateOf("")
    var prodTotal by mutableStateOf(0)
    
    // Clientes
    var clientes by mutableStateOf<List<ClientEntity>>(emptyList())
    var cliPage by mutableStateOf(0)
    var cliQuery by mutableStateOf("")
    
    // Ventas
    var ventas by mutableStateOf<List<SaleEntity>>(emptyList())
    var ventaPage by mutableStateOf(0)
    var ventaDetalle by mutableStateOf<Pair<SaleEntity,List<SaleItemEntity>>?>(null)

    // Ajustes
    var settings by mutableStateOf(SettingsEntity())
    
    // Carrito
    private val _cart = MutableStateFlow<List<CartLine>>(emptyList())
    val cart: StateFlow<List<CartLine>> = _cart.asStateFlow()
    val cartTotal = _cart.map { list -> list.sumOf { it.product.precio * it.qty } }.stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)
    val cartCount = _cart.map { it.sumOf { l -> l.qty } }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    init { refreshAll(); loadSettings() }

    fun refreshAll(){ refreshProductos(); refreshClientes(); refreshVentas() }

    fun refreshProductos(reset: Boolean = true){
        if(reset) prodPage = 0
        viewModelScope.launch {
            prodTotal = repo.countProductos()
            productos = repo.getProductos(prodPage, prodQuery)
        }
    }
    fun nextProdPage(){ prodPage++; refreshProductos(false) }
    fun prevProdPage(){ if(prodPage>0){ prodPage--; refreshProductos(false) } }

    fun refreshClientes(reset: Boolean=true){
        if(reset) cliPage=0
        viewModelScope.launch { clientes = repo.getClientes(cliPage, cliQuery) }
    }
    fun nextCliPage(){ cliPage++; refreshClientes(false) }
    fun prevCliPage(){ if(cliPage>0){ cliPage--; refreshClientes(false) } }

    fun refreshVentas(reset:Boolean=true){
        if(reset) ventaPage=0
        viewModelScope.launch { ventas = repo.getVentas(ventaPage) }
    }
    fun nextVentaPage(){ ventaPage++; refreshVentas(false) }
    fun prevVentaPage(){ if(ventaPage>0){ ventaPage--; refreshVentas(false) } }

    fun loadSettings(){ viewModelScope.launch { settings = repo.getSettings() } }
    fun saveSettings(s: SettingsEntity){ viewModelScope.launch { repo.saveSettings(s); settings=s } }

    // CRUD
    fun saveProduct(p: ProductEntity, onDone:()->Unit){
        viewModelScope.launch { repo.upsertProducto(p); refreshProductos(); onDone() }
    }
    fun deleteProduct(p: ProductEntity){ viewModelScope.launch { repo.deleteProducto(p); refreshProductos() } }

    fun saveClient(c: ClientEntity, onDone:()->Unit){
        viewModelScope.launch { repo.upsertCliente(c); refreshClientes(); onDone() }
    }
    fun deleteClient(c: ClientEntity){ viewModelScope.launch { repo.deleteCliente(c); refreshClientes() } }

    // Cart logic
    fun addToCart(p: ProductEntity){
        val current = _cart.value.toMutableList()
        val idx = current.indexOfFirst { it.product.id == p.id }
        if(idx>=0){ if(current[idx].qty < p.stock) current[idx]=current[idx].copy(qty=current[idx].qty+1) }
        else current.add(CartLine(p,1))
        _cart.value = current
    }
    fun incCart(id: Long){ _cart.value = _cart.value.map { if(it.product.id==id) it.copy(qty=it.qty+1) else it } }
    fun decCart(id: Long){
        _cart.value = _cart.value.mapNotNull {
            if(it.product.id==id){ if(it.qty>1) it.copy(qty=it.qty-1) else null } else it
        }
    }
    fun removeCart(id: Long){ _cart.value = _cart.value.filterNot { it.product.id==id } }
    fun clearCart(){ _cart.value = emptyList() }

    fun checkout(cliente: ClientEntity?, onDone:(Long)->Unit){
        viewModelScope.launch {
            val items = _cart.value.map { it.product to it.qty }
            if(items.isEmpty()) return@launch
            val id = repo.createVenta(cliente?.id, cliente?.nombre ?: "Público General", items)
            clearCart(); refreshProductos(); refreshVentas()
            onDone(id)
        }
    }
    fun loadVentaDetalle(v: SaleEntity){
        viewModelScope.launch {
            val items = repo.getVentaItems(v.id)
            ventaDetalle = v to items
        }
    }
}
