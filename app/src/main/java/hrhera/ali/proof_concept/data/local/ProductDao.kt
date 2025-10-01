package hrhera.ali.proof_concept.data.local

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import hrhera.ali.proof_concept.data.local.entities.ProductEntity
import hrhera.ali.proof_concept.domain.request.FiltersProducts

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<ProductEntity>

    @Query("SELECT COUNT(*) FROM products")
    suspend fun geItemsCount(): Int

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity?


    suspend fun getProducts(query: FiltersProducts): List<ProductEntity> = getProductsFiltered(buildQuery(query))

    @RawQuery
    suspend fun getProductsFiltered(query: SupportSQLiteQuery): List<ProductEntity>


    private fun buildQuery(filters: FiltersProducts): SupportSQLiteQuery {
        val sql = StringBuilder("SELECT * FROM products WHERE type='${filters.type}'")
        val args = mutableListOf<Any>()

        filters.name?.let {
            sql.append(" AND name LIKE ?")
            args.add("%$it%")
        }

        filters.price?.let {
            sql.append(" AND price >= ?")
            args.add(it)
        }
        sql.append(" ORDER BY price ${filters.orderingType.name}")
        val offset = (filters.page - 1) * filters.pageSize
        sql.append(" LIMIT ? OFFSET ?")
        args.add(filters.pageSize)
        args.add(offset)

        return SimpleSQLiteQuery(sql.toString(), args.toTypedArray())
    }
}