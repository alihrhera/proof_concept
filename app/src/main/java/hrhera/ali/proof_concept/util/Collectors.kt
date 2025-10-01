package hrhera.ali.proof_concept.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import hrhera.ali.proof_concept.domain.model.BaseResponse
import hrhera.ali.proof_concept.domain.model.Error
import hrhera.ali.proof_concept.domain.model.Loading
import hrhera.ali.proof_concept.domain.model.PaginatedResult
import hrhera.ali.proof_concept.domain.model.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


inline fun <T : Any> ViewModel.collectPaging(
    pageSize: Int = 20,
    crossinline onLoading: (Boolean) -> Unit,
    crossinline onError: (String?) -> Unit,
    crossinline fetchPage: suspend (page: Int, pageSize: Int) -> Flow<BaseResponse<PaginatedResult<T>>>
): Flow<PagingData<T>> {
    return Pager(
        config = PagingConfig(pageSize = pageSize),
        pagingSourceFactory = {
            object : PagingSource<Int, T>() {
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
                    return try {
                        val page = params.key ?: 1
                        var result: PaginatedResult<T>? = null
                        fetchPage(page, pageSize).collectResponse(onLoading, onError) { response ->
                            result = response
                        }
                        LoadResult.Page(
                            data = result?.data ?: emptyList(),
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (result?.isLastPage == true) null else page + 1
                        )
                    } catch (e: Exception) {
                        LoadResult.Error(e)
                    }
                }

                override fun getRefreshKey(state: PagingState<Int, T>): Int? {
                    return state.anchorPosition?.let { anchorPosition ->
                        val anchorPage = state.closestPageToPosition(anchorPosition)
                        anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                    }
                }
            }
        }
    ).flow.cachedIn(viewModelScope)
}

suspend inline fun <T : Any> Flow<BaseResponse<T>>.collectResponse(
    crossinline onLoading: (Boolean) -> Unit,
    crossinline onError: (String?) -> Unit,
    crossinline onSuccess: (T) -> Unit,
) {
    this.collectLatest { response ->
        onLoading(response is Loading)
        when (response) {
            is Error -> onError(response.message)
            is Success<T> -> onSuccess(response.data)
            else -> {}
        }
    }

}


inline fun ViewModel.initTask(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline task: suspend () -> Unit
) = viewModelScope.launch(dispatcher) {
    task()
}
