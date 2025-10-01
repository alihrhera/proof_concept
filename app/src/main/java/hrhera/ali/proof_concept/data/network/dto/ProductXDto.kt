package hrhera.ali.proof_concept.data.network.dto

// this will be converted from json response
data class ProductXDto(
    val id: Int,
    val name: String,
    val price: String?,
    val category: Int,
    val image: String,
    val description: String,
    val rating: Float,
    val stock: Int,
    val brand: String,
)