package hrhera.ali.proof_concept.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val price: Float,
    val image: String,
    val description: String,
    val rating: Float,
    val stock: Int,
    val type: String
)