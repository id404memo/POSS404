
package com.memo.poss404.data.local.dao
import androidx.room.*
import com.memo.poss404.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM productos WHERE nombre LIKE '%' || :q || '%' OR codigo LIKE '%' || :q || '%' ORDER BY id DESC LIMIT 33 OFFSET :offset")
    suspend fun searchPaged(q: String, offset: Int): List<ProductEntity>
    @Query("SELECT * FROM productos ORDER BY id DESC LIMIT 33 OFFSET :offset")
    suspend fun getPaged(offset: Int): List<ProductEntity>
    @Query("SELECT COUNT(*) FROM productos")
    suspend fun count(): Int
    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun getById(id: Long): ProductEntity?
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(p: ProductEntity): Long
    @Update suspend fun update(p: ProductEntity)
    @Delete suspend fun delete(p: ProductEntity)
    @Query("SELECT * FROM productos WHERE codigo = :codigo LIMIT 1")
    suspend fun getByCodigo(codigo: String): ProductEntity?
}

@Dao
interface ClientDao {
    @Query("SELECT * FROM clientes ORDER BY id DESC LIMIT 33 OFFSET :offset")
    suspend fun getPaged(offset: Int): List<ClientEntity>
    @Query("SELECT * FROM clientes WHERE nombre LIKE '%' || :q || '%' ORDER BY id DESC LIMIT 33 OFFSET :offset")
    suspend fun search(q: String, offset: Int): List<ClientEntity>
    @Query("SELECT COUNT(*) FROM clientes") suspend fun count(): Int
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(c: ClientEntity): Long
    @Update suspend fun update(c: ClientEntity)
    @Delete suspend fun delete(c: ClientEntity)
    @Query("SELECT * FROM clientes") fun getAllFlow(): Flow<List<ClientEntity>>
}

@Dao
interface SaleDao {
    @Query("SELECT * FROM ventas WHERE fecha >= :startDay ORDER BY fecha DESC LIMIT 33 OFFSET :offset")
    suspend fun getVentasDelDia(startDay: Long, offset: Int): List<SaleEntity>
    @Query("SELECT * FROM ventas ORDER BY fecha DESC LIMIT 33 OFFSET :offset")
    suspend fun getPaged(offset: Int): List<SaleEntity>
    @Insert suspend fun insertVenta(v: SaleEntity): Long
    @Insert suspend fun insertItems(items: List<SaleItemEntity>)
    @Query("SELECT * FROM venta_items WHERE ventaId = :ventaId")
    suspend fun getItems(ventaId: Long): List<SaleItemEntity>
    @Query("SELECT COUNT(*) FROM ventas") suspend fun count(): Int
}

@Dao
interface SettingsDao {
    @Query("SELECT * FROM ajustes WHERE id = 1") suspend fun get(): SettingsEntity?
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun save(s: SettingsEntity)
}
