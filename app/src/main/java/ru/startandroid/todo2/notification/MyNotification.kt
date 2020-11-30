package ru.startandroid.todo2.notification

import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.startandroid.todo2.MainActivity
import ru.startandroid.todo2.R
import java.util.*

class MyNotification(context: Context) {

    private var counter: Int=0
    private val mContext=context
    // Идентификатор канала
    private val CHANNEL_ID = MainActivity.CHANNEL_ID
    private var builder: NotificationCompat.Builder?=null

    init{


            builder = NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.today)
                .setContentTitle("textTitle")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }
    fun notifyMyNotification(text : String){

        with(NotificationManagerCompat.from(mContext)) {
            // notificationId is a unique int for each notification that you must define
            builder?.setContentText(text)
            builder?.build()?.let { notify(Random().nextInt(), it) }
        }
    }

}