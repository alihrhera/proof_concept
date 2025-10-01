package hrhera.ali.proof_concept.domain.usecase

import android.util.Log
import hrhera.ali.proof_concept.data.network.dto.ProductXDto
import hrhera.ali.proof_concept.domain.model.BaseResponse
import hrhera.ali.proof_concept.domain.model.PaginatedResult
import hrhera.ali.proof_concept.domain.model.Product
import hrhera.ali.proof_concept.domain.repository.ProductRepository
import hrhera.ali.proof_concept.domain.request.FiltersProducts
import hrhera.ali.proof_concept.domain.request.filtersProducts
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsPageUseCase @Inject constructor(
     val repository: ProductRepository
) {
    suspend operator fun invoke(
        filters: FiltersProducts,
    ): Flow<BaseResponse<PaginatedResult<Product>>>  {
        return repository.fetchProducts(filters.filtersProducts<ProductXDto>())
    }

}