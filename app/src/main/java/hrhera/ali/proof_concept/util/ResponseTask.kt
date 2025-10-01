package hrhera.ali.proof_concept.util

import hrhera.ali.proof_concept.domain.model.BaseResponse
import hrhera.ali.proof_concept.domain.model.Loading
import hrhera.ali.proof_concept.domain.model.Error
import hrhera.ali.proof_concept.domain.model.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

fun <T> buildTask(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    task: suspend () -> T
): Flow<BaseResponse<T>> = flow<BaseResponse<T>> {
    val result = task()
    emit(Success(result))
}.flowOn(dispatcher)
    .onStart {
        emit(Loading)
    }.catch {
        emit(Error(it.message ?: "Unknown error"))
    }


