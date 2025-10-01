package hrhera.ali.proof_concept.data.network

import hrhera.ali.proof_concept.data.network.dto.ProductDto
import hrhera.ali.proof_concept.data.network.dto.ProductResponse
import hrhera.ali.proof_concept.data.network.dto.ProductXDto
import hrhera.ali.proof_concept.data.network.dto.ProductXResponse
import kotlinx.coroutines.delay

class FakeApi : ApiService {

    private val allXProducts = List(100) { i ->
        ProductXDto(
            id = i + 1, name = "Product #${i + 1}",
            price = "${(10..1000).random().toFloat()}",
            category = (10..100).random(),
            image = "",
            description = "",
            rating = (0..5).random().toFloat(),
            stock = (0..100).random(),
            brand = "",
        )
    }

    private val allProducts = List(100) { i ->
        ProductDto(
            id = i + 1, name = "Product #${i + 1}",
            price = "${(10..1000).random().toFloat()}",
            category = (10..100).random(),
            image = "",
            description = "",
            rating = (0..5).random().toFloat(),
            stoke = (0..100).random(),
            someChange = listOf(true, false).random(),
            someOtherChange = "",
            someOtherChange2 = "",
            someOtherChange3 = "",
        )
    }

    override suspend fun getXProducts(page: Int, pageSize: Int): ProductXResponse {
        delay(3000)
        val fromIndex = (page - 1) * pageSize
        val lastPage = allXProducts.size / pageSize
        if (fromIndex >= allXProducts.size) return ProductXResponse(
            products = emptyList(),
            total = allXProducts.size,
            lastPage = lastPage,
        )
        val toIndex = minOf(fromIndex + pageSize, allXProducts.size)
        val item = allXProducts.subList(fromIndex, toIndex)
        return ProductXResponse(
            products = item,
            total = allXProducts.size,
            lastPage = lastPage,
        )
    }


    override suspend fun getProducts(page: Int, pageSize: Int): ProductResponse {
        delay(3000)
        val fromIndex = (page - 1) * pageSize
        val lastPage = allProducts.size / pageSize
        if (fromIndex >= allProducts.size) return ProductResponse(
            products = emptyList(),
            total = allProducts.size,
            lastPage = lastPage,
        )
        val toIndex = minOf(fromIndex + pageSize, allProducts.size)
        val item = allProducts.subList(fromIndex, toIndex)

        return ProductResponse(
            products = item,
            total = allProducts.size,
            lastPage = lastPage,
        )
    }

}
