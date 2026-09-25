
package com.memo.poss404.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val codigo: String,
    val nombre: String,
    val precio: Double,
    val stock: Int,
    val categoria: String = "General",
    val creado: Long = System.currentTimeMillis()
)
