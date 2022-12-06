package com.example.coopproject.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.coopproject.MainActivity
import com.example.coopproject.R
import java.util.*

class NotificationAlarmReceiver: BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
        try{
            val calendar: Calendar = Calendar.getInstance()
            showNotification(context,"Woohoo! It's ${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}." +
                    " Get Ready!",
                "Click here to start.")
        }catch (exception: Exception){
            Log.d("exceptionLog",exception.printStackTrace().toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showNotification(context: Context?, title: String, description: String){
        Log.d("Notification","Notification Triggered")
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // TODO: refactor strings to constants.kt
        val channelName = ChannelInformationForNotification.Channel_NAME.name
        val channelId = ChannelInformationForNotification.Channel_NAME.name

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        val resultIntent = Intent(context,MainActivity::class.java)
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(resultIntent)
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }

        // design the notification.
        val builder = NotificationCompat.Builder(context,channelId).
                setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_baseline_start_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, R.color.purple_200))
            .setContentIntent(resultPendingIntent)

        manager.notify(1,builder.build())
    }
}