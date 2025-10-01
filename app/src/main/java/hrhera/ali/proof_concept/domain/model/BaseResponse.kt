package hrhera.ali.proof_concept.domain.model

sealed class BaseResponse<out T>
data class Success<T>(val data: T) : BaseResponse<T>()
data class Error(val message: String) : BaseResponse<Nothing>()
data object Loading : BaseResponse<Nothing>()
data object Idle : BaseResponse<Nothing>()