
package com.memo.poss404

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class Producto(val id: Int, val nombre: String, val sku: String, val precio: Double, var stock: Int, val categoria: String = "General")
data class Cliente(val id: Int, val nombre: String, val telefono: String = "")
data class Venta(val id: Int, val total: Double, val fecha: String, val cliente: String, val pagada: Boolean = true)

class PosViewModel : ViewModel() {
    val productos = mutableStateListOf(
        Producto(1, "Coca Cola 600ml", "SKU-001", 18.0, 50, "Bebidas"),
        Producto(2, "Sabritas Original 45g", "SKU-002", 15.0, 120, "Botanas"),
        Producto(3, "Pan Bimbo Blanco 680g", "SKU-003", 32.0, 30, "Pan"),
        Producto(4, "Leche Lala Entera 1L", "SKU-004", 26.5, 80, "Lácteos"),
        Producto(5, "Huevo San Juan 18pz", "SKU-005", 48.0, 40, "Abarrotes")
    )
    val carrito = mutableStateListOf<Producto>()
    val clientes = mutableStateListOf(
        Cliente(1, "Cliente Mostrador", ""),
        Cliente(2, "José García", "222 123 4567"),
        Cliente(3, "María López", "222 987 6543")
    )
    val ventas = mutableStateListOf(
        Venta(1, 156.50, "23 sept 2026", "José García", true),
        Venta(2, 89.00, "23 sept 2026", "Cliente Mostrador", true)
    )
    var searchQuery by mutableStateOf("")
    var selectedCliente by mutableStateOf(clientes[0])
    
    fun addToCart(p: Producto) { carrito.add(p) }
    fun removeFromCart(p: Producto) { carrito.remove(p) }
    fun cobrar() {
        if(carrito.isEmpty()) return
        val total = carrito.sumOf { it.precio } * 1.16
        ventas.add(0, Venta(ventas.size+1, total, "Hoy", selectedCliente.nombre, true))
        carrito.clear()
    }
    val filteredProductos get() = if(searchQuery.isBlank()) productos else productos.filter { it.nombre.contains(searchQuery, true) || it.sku.contains(searchQuery, true) }
    val totalConIva get() = carrito.sumOf { it.precio } * 1.16
    val subtotal get() = carrito.sumOf { it.precio }
}
