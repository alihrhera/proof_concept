package hrhera.ali.proof_concept.util

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import hrhera.ali.proof_concept.data.sync.SyncProductsWorker
import hrhera.ali.proof_concept.domain.repository.ProductRepository

class ManualWorkerFactory(
    private val repository: ProductRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        Log.d("ManualWorkerFactory", "createWorker for $workerClassName")
        return if (workerClassName == SyncProductsWorker::class.java.name) {
            SyncProductsWorker(appContext, workerParameters, repository)
        } else {
            null
        }
    }
}