
package com.memo.poss404.data.repository
import com.memo.poss404.data.local.AppDatabase
import com.memo.poss404.data.local.entity.*

class PosRepository(private val db: AppDatabase) {
    // Productos
    suspend fun getProductos(page: Int, query: String = ""): List<ProductEntity> {
        val offset = page * 33
        return if(query.isBlank()) db.productDao().getPaged(offset) else db.productDao().searchPaged(query, offset)
    }
    suspend fun countProductos() = db.productDao().count()
    suspend fun upsertProducto(p: ProductEntity) = if(p.id==0L) db.productDao().insert(p) else { db.productDao().update(p); p.id }
    suspend fun deleteProducto(p: ProductEntity) = db.productDao().delete(p)

    // Clientes
    suspend fun getClientes(page: Int, q: String="") = if(q.isBlank()) db.clientDao().getPaged(page*33) else db.clientDao().search(q, page*33)
    suspend fun countClientes() = db.clientDao().count()
    suspend fun upsertCliente(c: ClientEntity) = if(c.id==0L) db.clientDao().insert(c) else { db.clientDao().update(c); c.id }
    suspend fun deleteCliente(c: ClientEntity) = db.clientDao().delete(c)
    fun clientesFlow() = db.clientDao().getAllFlow()

    // Ventas
    suspend fun getVentas(page: Int): List<SaleEntity> = db.saleDao().getPaged(page*33)
    suspend fun getVentasHoy(): List<SaleEntity> {
        val start = java.util.Calendar.getInstance().apply { set(java.util.Calendar.HOUR_OF_DAY,0); set(java.util.Calendar.MINUTE,0); set(java.util.Calendar.SECOND,0) }.timeInMillis
        return db.saleDao().getVentasDelDia(start,0)
    }
    suspend fun createVenta(clienteId: Long?, clienteNombre: String, items: List<Pair<ProductEntity,Int>>): Long {
        val total = items.sumOf { it.first.precio * it.second }
        val ventaId = db.saleDao().insertVenta(SaleEntity(clienteNombre=clienteNombre, clienteId=clienteId, total=total, itemsCount=items.sumOf { it.second }))
        val saleItems = items.map { (prod,cant) ->
            SaleItemEntity(ventaId=ventaId, productoId=prod.id, nombre=prod.nombre, precio=prod.precio, cantidad=cant)
        }
        db.saleDao().insertItems(saleItems)
        // descontar stock
        items.forEach { (p,c) -> db.productDao().update(p.copy(stock = (p.stock - c).coerceAtLeast(0))) }
        return ventaId
    }
    suspend fun getVentaItems(id: Long) = db.saleDao().getItems(id)

    // Ajustes
    suspend fun getSettings() = db.settingsDao().get() ?: SettingsEntity()
    suspend fun saveSettings(s: SettingsEntity) = db.settingsDao().save(s)
}
