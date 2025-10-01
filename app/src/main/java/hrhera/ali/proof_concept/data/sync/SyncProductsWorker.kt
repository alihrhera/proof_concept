package hrhera.ali.proof_concept.data.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import hrhera.ali.proof_concept.domain.repository.ProductRepository

class SyncProductsWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val repository: ProductRepository
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            repository.syncProductsFromApi()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
