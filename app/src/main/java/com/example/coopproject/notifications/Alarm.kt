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
import com.example.coopproject.MainActivity
import com.example.coopproject.R

class Alarm : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
        try{
            showNotification(context,"Reminder","Exercise")
        }catch (exception: Exception){
            Log.d("exceptionLog",exception.printStackTrace().toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showNotification(context: Context?, title: String, description: String){
        Log.d("Araf","Notification Triggered")
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelName = "Reminder"
        val channelId = "Reminder Id"

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

        val builder = NotificationCompat.Builder(context,channelId).
                setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_baseline_add_circle_24)
            .setContentIntent(resultPendingIntent)

        manager.notify(1,builder.build())

    }
}