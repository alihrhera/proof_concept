package hrhera.ali.proof_concept.domain.repository

import hrhera.ali.proof_concept.domain.model.BaseResponse
import hrhera.ali.proof_concept.domain.model.PaginatedResult
import hrhera.ali.proof_concept.domain.model.Product
import hrhera.ali.proof_concept.domain.request.FiltersProducts
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun fetchProducts(filters: FiltersProducts):  Flow<BaseResponse<PaginatedResult<Product>>>

    suspend fun syncProductsFromApi()
}