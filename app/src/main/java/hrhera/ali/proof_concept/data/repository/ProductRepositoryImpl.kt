package hrhera.ali.proof_concept.data.repository

import hrhera.ali.proof_concept.data.local.ProductDao
import hrhera.ali.proof_concept.data.local.entities.ProductEntity
import hrhera.ali.proof_concept.data.network.ApiService
import hrhera.ali.proof_concept.domain.model.BaseResponse
import hrhera.ali.proof_concept.domain.model.PaginatedResult
import hrhera.ali.proof_concept.domain.model.Product
import hrhera.ali.proof_concept.domain.repository.ProductRepository
import hrhera.ali.proof_concept.domain.request.FiltersProducts
import hrhera.ali.proof_concept.util.buildTask
import hrhera.ali.proof_concept.util.mapItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: ProductDao,
) : ProductRepository {


    override suspend fun fetchProducts(filters: FiltersProducts): Flow<BaseResponse<PaginatedResult<Product>>> {

        return buildTask {
            val products = dao.getProducts(filters).map { item ->
                item.mapItem { value ->
                    Product(
                        id = value.id,
                        name = value.name,
                        price = value.price,
                        image = value.image,
                        rating = value.rating,
                        stock = value.stock,
                    )
                }
            }
            val lastPage = dao.geItemsCount() / filters.pageSize
            val isLastPage = filters.page == lastPage
            PaginatedResult(
                data = products,
                currentPage = filters.pageSize,
                totalPages = lastPage,
                isLastPage = isLastPage
            )
        }
    }

    override suspend fun syncProductsFromApi() {
        syncProducts()
        syncXProducts()
    }

    private suspend fun syncProducts() {
        var pageNumber = 1
        var lastPageValue = 2
        do {
            with(api.getProducts(page = pageNumber, pageSize = 10)) {
                pageNumber++
                lastPageValue = this.lastPage
                dao.insertProducts(
                    products.map { item ->
                        item.mapItem { item ->
                            ProductEntity(
                                id = item.id,
                                name = item.name,
                                price = item.price?.toFloat() ?: 0f,
                                image = item.image,
                                description = item.description,
                                rating = item.rating,
                                stock = item.stoke,
                                type = item::class.simpleName.toString()
                            )
                        }
                    }
                )
            }
        } while (pageNumber <= lastPageValue)
    }

    private suspend fun syncXProducts() {
        var page = 1
        var lastPage = 2
        do {
            api.getXProducts(page = page, pageSize = 10).also {
                page++
                lastPage = it.lastPage
            }.products.forEach {
                dao.insertProduct(
                    it.mapItem { item ->
                        ProductEntity(
                            id = item.id,
                            name = item.name,
                            price = item.price?.toFloat() ?: 0f,
                            image = item.image,
                            description = item.description,
                            rating = item.rating,
                            stock = item.stock,
                            type = item::class.simpleName.toString()
                        )
                    }
                )
            }
        } while (page <= lastPage)
    }

}