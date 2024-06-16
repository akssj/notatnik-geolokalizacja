package com.example.notatnik_geolokalizacja.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.notatnik_geolokalizacja.R

/**
 * Class for creating and displaying notifications.
 * @param context
 * @param params Parameters to construct the worker(time delay).
 */
class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    /**
     * Init to send notification
     * @return Result indicating success or failure.
     */
    override fun doWork(): Result {
        sendNotification()
        return Result.success()
    }

    /**
     * Method to create and display a notification.
     */
    private fun sendNotification() {
        val channelId = "channel_id"
        val name = "Channel_name"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Notification title")
            .setContentText("Notification description")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(1, builder.build())
        }
    }
}
