package hrhera.ali.proof_concept.domain.request

import hrhera.ali.proof_concept.util.OrderingType
import kotlin.jvm.java

data class FiltersProducts(
    val name: String? = null,
    val price: Float? = null,
    val orderingType: OrderingType = OrderingType.ASC,
    val page: Int,
    val pageSize: Int,
    val type: String = ""
)

inline fun <reified T : Any> FiltersProducts.filtersProducts(): FiltersProducts {
    return this.copy(
        type = T::class.simpleName ?: "Unknown"
    )
}