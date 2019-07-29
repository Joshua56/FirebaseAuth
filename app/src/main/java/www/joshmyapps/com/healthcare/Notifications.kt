package www.joshmyapps.com.healthcare

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

object Notifications {
    private const val notificationTitle = "Symptoms"
    private const val CHANNEL_ID = ""
    fun sendNotification(context: Context, content: String): NotificationCompat.Builder {
        createNotificationChannel(context)
        return NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_sent)
                .setContentTitle(notificationTitle)
                .setContentText(String.format("%s information sent", content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Disease Notification"
            val descriptionText = "Displays Status of sent disease information."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}