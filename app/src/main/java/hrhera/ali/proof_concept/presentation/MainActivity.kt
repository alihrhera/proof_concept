package hrhera.ali.proof_concept.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import hrhera.ali.proof_concept.presentation.products.ProductListContent
import hrhera.ali.proof_concept.presentation.ui.theme.Proof_conceptTheme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proof_conceptTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductListScreen(innerPadding)
                }
            }
        }
    }
}

@Composable
fun ProductListScreen(paddingValues: PaddingValues, viewModel: ProductsViewModel = hiltViewModel()) {
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val products = viewModel.products.collectAsLazyPagingItems()

    ProductListContent(
        products = products,
        isLoading = loading,
        errorMessage = error,
        onRetry = { products.retry() }
    )
}