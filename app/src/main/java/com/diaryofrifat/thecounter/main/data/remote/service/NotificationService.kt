package com.diaryofrifat.thecounter.main.data.remote.service

import android.app.PendingIntent
import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.ui.app.landing.container.ContainerActivity
import com.diaryofrifat.thecounter.utils.helper.notification.NotificationUtils

/**
 * This is the notification service class of the project. This class contains all the
 * basic methods needed for the FCM push notifications.
 * @author Mohd. Asfaq-E-Azam Rifat
 */
class NotificationService : FirebaseMessagingService() {

    companion object {
        const val TITLE = "title"
        const val BODY = "body"
        const val SHORT_CONTENT = "short_content"
    }

    override fun onMessageReceived(message: RemoteMessage) {
        showNotification(message)
    }

    private fun showNotification(message: RemoteMessage) {
        if (message.data != null) {
            var title: String? = null
            var body: String? = null
            var shortContent: String? = null

            if (message.data.containsKey(TITLE)) {
                title = message.data[TITLE]
            }

            if (message.data.containsKey(BODY)) {
                body = message.data[BODY]
            }

            if (message.data.containsKey(SHORT_CONTENT)) {
                shortContent = message.data[SHORT_CONTENT]
            }

            if (title != null && body != null && shortContent != null) {
                NotificationUtils.buildNotification(message.hashCode(),
                        NotificationUtils.NotificationType.DEFAULT,
                        R.drawable.ic_app_icon,
                        title, shortContent, body, preparePendingIntent())
            }
        }
    }

    private fun preparePendingIntent(): PendingIntent? {
        val intent = Intent(this, ContainerActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra(ContainerActivity::class.java.simpleName, true)

        return PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_ONE_SHOT)
    }
}