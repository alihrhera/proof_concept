package hrhera.ali.proof_concept.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import hrhera.ali.proof_concept.domain.request.FiltersProducts
import hrhera.ali.proof_concept.domain.usecase.GetProductsPageUseCase
import hrhera.ali.proof_concept.util.ManualWorkerFactory
import hrhera.ali.proof_concept.util.buildTask
import hrhera.ali.proof_concept.util.collectPaging
import hrhera.ali.proof_concept.util.initTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsPageUseCase: GetProductsPageUseCase
) : ViewModel() {

    init {
        initSync()
    }

    fun initSync() {
        // skip this as it fake work manger
        initTask {
            getProductsPageUseCase.repository.syncProductsFromApi()
        }
    }

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    val products = collectPaging(
        onLoading = { isLoading -> _loading.value = isLoading },
        onError = { errorMessage -> _error.value = errorMessage },
        fetchPage = { page, pageSize ->
            _error.value = null
            getProductsPageUseCase(
                filters = FiltersProducts(
                    page = page,
                    pageSize = pageSize,
                )
            )
        })


}