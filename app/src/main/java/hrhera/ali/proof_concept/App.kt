package hrhera.ali.proof_concept

import android.app.Application
import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import hrhera.ali.proof_concept.data.sync.SyncProductsWorker
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class App : Application(){


    override fun onCreate() {
        super.onCreate()
        scheduleSyncOnNetworkAvailable(this)
    }

    private fun scheduleSyncOnNetworkAvailable(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWork = PeriodicWorkRequestBuilder<SyncProductsWorker>(
            1, TimeUnit.MINUTES
        ).setConstraints(constraints)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "sync_on_network",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWork
        )
    }

}