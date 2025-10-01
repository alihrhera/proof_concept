package hrhera.ali.proof_concept.domain.model

data class PaginatedResult<T>(
    val data: List<T>,
    val currentPage: Int,
    val totalPages: Int?,
    val isLastPage: Boolean
)
