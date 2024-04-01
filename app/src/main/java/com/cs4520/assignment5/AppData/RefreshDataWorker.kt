package com.cs4520.assignment5.AppData

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cs4520.assignment5.API.Api
import com.cs4520.assignment5.database.CacheDatabase

class RefreshDataWorker(
    private val appContext: Context,
    workerParams: WorkerParameters,
    ) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val remoteSource = Api.apiService
            val localDB = CacheDatabase.instance(appContext)
            val repo = ProductRepository(remoteSource, localDB, appContext)
            repo.getProducts((1..10).random())
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

}
