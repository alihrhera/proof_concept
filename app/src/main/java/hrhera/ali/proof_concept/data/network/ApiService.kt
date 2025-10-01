package hrhera.ali.proof_concept.data.network

import hrhera.ali.proof_concept.data.network.dto.ProductResponse
import hrhera.ali.proof_concept.data.network.dto.ProductXResponse

interface ApiService {
    suspend fun getProducts(page: Int, pageSize: Int): ProductResponse

    suspend fun getXProducts(page: Int, pageSize: Int): ProductXResponse

}