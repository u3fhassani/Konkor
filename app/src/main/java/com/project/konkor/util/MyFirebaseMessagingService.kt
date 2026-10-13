package com.project.konkor.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.project.konkor.R
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        const val DESTINATION_TYPE = "destinationType"
        const val DESTINATION_TYPE_URL = "url"
        const val DESTINATION_TYPE_BIG_PICTURE = "bigPicture"
        const val DESTINATION_TYPE_BIG_TEXT = "bigText"

        const val IMAGE_URL_BIG_PICTURE = "imageUrl"
        const val BIG_TEXT = "text"
        const val LOAD_URL = "loadUrl"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("firebase", "Refreshed FCM token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        Log.d("firebase", "onMessageReceived:$remoteMessage ")

        if (remoteMessage.data.isNotEmpty()) {
            val dataMessage = remoteMessage.data
            val loadUrl = dataMessage[LOAD_URL]

            val intent = Intent(Intent.ACTION_VIEW).apply {
                putExtra("url", loadUrl)
            }

            val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }

            val pendingIntent = PendingIntent.getActivity(
                this, 0,
                Intent.createChooser(intent, "انتخاب مرورگر..."),
                flags
            )

            when (dataMessage[DESTINATION_TYPE]) {
                DESTINATION_TYPE_URL -> {
                    remoteMessage.notification?.let {
                        urlNotification(it, notificationManager, pendingIntent)
                    }
                }
                DESTINATION_TYPE_BIG_TEXT -> {
                    remoteMessage.notification?.let {
                        bigTextNotification(
                            it,
                            notificationManager,
                            dataMessage[BIG_TEXT],
                            pendingIntent
                        )
                    }
                }
            }
        } else {
            remoteMessage.notification?.let {
                normalNotification(it, notificationManager)
            }
        }
    }

    private fun normalNotification(
        remoteNotification: RemoteMessage.Notification,
        notificationManager: NotificationManager
    ) {
        val notification = NotificationCompat.Builder(
            this,
            getString(R.string.default_notification_channel_id)
        )
            .setSmallIcon(R.drawable.ic_graduate)
            .setAutoCancel(true)
            .setContentTitle(remoteNotification.title)
            .setContentText(remoteNotification.body)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }

    private fun urlNotification(
        remoteNotification: RemoteMessage.Notification,
        notificationManager: NotificationManager,
        pendingIntent: PendingIntent
    ) {
        val notification = NotificationCompat.Builder(
            this,
            getString(R.string.default_notification_channel_id)
        )
            .setSmallIcon(R.drawable.ic_graduate)
            .setAutoCancel(true)
            .setContentTitle(remoteNotification.title)
            .setContentText(remoteNotification.body)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }

    private fun bigTextNotification(
        remoteNotification: RemoteMessage.Notification,
        notificationManager: NotificationManager,
        text: String?,
        pendingIntent: PendingIntent
    ) {
        val bigTextNotification = NotificationCompat.Builder(
            this,
            getString(R.string.default_notification_channel_id)
        )
            .setSmallIcon(R.drawable.ic_graduate)
            .setAutoCancel(true)
            .setContentTitle(remoteNotification.title)
            .setContentText(remoteNotification.body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(Random.nextInt(), bigTextNotification)
    }
}