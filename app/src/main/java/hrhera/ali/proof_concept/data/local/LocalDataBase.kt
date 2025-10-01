package hrhera.ali.proof_concept.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import hrhera.ali.proof_concept.data.local.entities.ProductEntity


@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
