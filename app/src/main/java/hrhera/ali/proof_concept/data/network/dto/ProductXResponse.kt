package hrhera.ali.proof_concept.data.network.dto

data class ProductXResponse (
    val products: List<ProductXDto>,
    val total: Int,
    val lastPage: Int,
)