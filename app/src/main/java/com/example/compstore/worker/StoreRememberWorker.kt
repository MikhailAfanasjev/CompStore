package com.example.compstore.worker

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StoreRememberWorker @Inject constructor(
    @ApplicationContext private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val taskName = inputData.getString("task_name") ?: return Result.failure()

        Log.d("StoreRememberWorker", "Received task name: $taskName")

        return try {
            if (checkNotificationPermission()) {
                showNotification(taskName)
                Log.d("StoreRememberWorker", "Notification shown successfully for task: $taskName")
                Result.success()
            } else {
                Log.w("StoreRememberWorker", "Notification permission denied.")
                Result.failure()
            }
        } catch (e: Exception) {
            Log.e("StoreRememberWorker", "Error showing notification: ${e.message}", e)
            Result.failure()
        }
    }

    private fun checkNotificationPermission(): Boolean {
        val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
        Log.d("StoreRememberWorker", "Notification permission status: $hasPermission")
        return hasPermission
    }

    private fun showNotification(taskName: String) {
        Log.d("StoreRememberWorker", "Preparing to show notification for task: $taskName")

        // Rest of the notification code...

        Log.d("StoreRememberWorker", "Notification setup completed.")
    }
}