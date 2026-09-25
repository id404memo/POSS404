
package com.memo.poss404.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class ClientEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val telefono: String = "",
    val email: String = "",
    val direccion: String = "",
    val creado: Long = System.currentTimeMillis()
)
