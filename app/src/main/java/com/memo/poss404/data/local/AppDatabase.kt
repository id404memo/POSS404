
package com.memo.poss404.data.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.memo.poss404.data.local.dao.*
import com.memo.poss404.data.local.entity.*

@Database(
    entities = [ProductEntity::class, ClientEntity::class, SaleEntity::class, SaleItemEntity::class, SettingsEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun clientDao(): ClientDao
    abstract fun saleDao(): SaleDao
    abstract fun settingsDao(): SettingsDao
}
