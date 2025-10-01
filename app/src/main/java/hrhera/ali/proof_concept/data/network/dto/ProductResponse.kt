package hrhera.ali.proof_concept.data.network.dto

data class ProductResponse (
    val products: List<ProductDto>,
    val total: Int,
    val lastPage: Int,
)