package hrhera.ali.proof_concept.data.network.dto

// this will be converted from json response
data class ProductDto(
    val id: Int,
    val name: String,
    val price: String?,
    val category: Int,
    val image: String,
    val description: String,
    val rating: Float,
    val stoke: Int,
    val someChange:Boolean,
    val someOtherChange: String,
    val someOtherChange2: String,
    val someOtherChange3: String,
)