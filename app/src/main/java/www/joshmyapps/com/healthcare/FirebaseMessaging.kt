package www.joshmyapps.com.healthcare

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseMessaging : FirebaseMessagingService() {

    companion object {
        val TAG = FirebaseMessaging::class.java.simpleName
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: ${remoteMessage?.from}")
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }
}