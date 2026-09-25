
package com.memo.poss404.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ventas")
data class SaleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val fecha: Long = System.currentTimeMillis(),
    val clienteId: Long? = null,
    val clienteNombre: String = "Público General",
    val total: Double,
    val itemsCount: Int
)

@Entity(tableName = "venta_items")
data class SaleItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val ventaId: Long,
    val productoId: Long,
    val nombre: String,
    val precio: Double,
    val cantidad: Int
)

@Entity(tableName = "ajustes")
data class SettingsEntity(
    @PrimaryKey val id: Int = 1,
    val nombreNegocio: String = "Mi Tienda 404",
    val direccion: String = "Puebla, México",
    val ticketFooter: String = "¡Gracias por su compra!"
)
