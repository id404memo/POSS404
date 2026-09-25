
package com.memo.poss404

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Producto(val id: Int, val nombre: String, val precio: Double, var stock: Int)
data class Cliente(val id: Int, val nombre: String)
data class Venta(val id: Int, val total: Double, val fecha: String)

class PosViewModel : ViewModel() {
    val productos = mutableStateListOf(
        Producto(1, "Coca Cola 600ml", 18.0, 50),
        Producto(2, "Sabritas 45g", 15.0, 100),
        Producto(3, "Pan Bimbo", 32.0, 30)
    )
    val carrito = mutableStateListOf<Producto>()
    val clientes = mutableStateListOf(Cliente(1, "Cliente Mostrador"))
    val ventas = mutableStateListOf<Venta>()

    fun addToCart(p: Producto) { carrito.add(p) }
    fun cobrar() {
        if(carrito.isEmpty()) return
        val total = carrito.sumOf { it.precio }
        ventas.add(Venta(ventas.size+1, total, "Hoy"))
        carrito.clear()
    }
}
