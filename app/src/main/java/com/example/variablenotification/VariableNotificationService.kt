package com.example.variablenotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

const val CHANNEL_ID = "VARIABLE_NOTIFICATION"
const val CHANNEL_NAME = "Foreground Channel"
const val FOREGROUND_ID = 2

@AndroidEntryPoint
class VariableNotificationService: Service() {
    @Inject
    lateinit var timerOperator: TimerOperator
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        val startTimerIntent = Intent(applicationContext, StartTimerService::class.java)
        val stopTimerIntent = Intent(applicationContext, StopTimerService::class.java)
        val closeTimerIntent = Intent(applicationContext, CloseTimerService::class.java)

        val startTimerPendingIntent = PendingIntent.getService(
            applicationContext,
            0,
            startTimerIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val stopTimerPendingIntent = PendingIntent.getService(
            applicationContext,
            0,
            stopTimerIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val closeTimerPendingIntent = PendingIntent.getService(
            applicationContext,
            0,
            closeTimerIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        updateNotification(startTimerPendingIntent, stopTimerPendingIntent, closeTimerPendingIntent)

        return START_NOT_STICKY
    }

    private fun updateNotification(startTimerPendingIntent: PendingIntent, stopTimerPendingIntent: PendingIntent, closeTimerPendingIntent: PendingIntent){
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask(){
            override fun run() {
                if(timerOperator.isStart){
                    startForeground(
                        FOREGROUND_ID,
                        notification(
                            "${timerOperator.timerSeconds} seconds",
                            stopTimerPendingIntent,
                            closeTimerPendingIntent
                        )
                    )
                }else{
                    startForeground(
                        FOREGROUND_ID,
                        notification(
                            "${timerOperator.timerSeconds} seconds",
                            startTimerPendingIntent,
                            closeTimerPendingIntent
                        )
                    )
                }

            }
        },0, 1000)
    }

    private fun notification(title: String, operateTimerPendingIntent: PendingIntent, closeTimerPendingIntent: PendingIntent) : Notification {
        val contentText = "Variable Notification"
        val channel = NotificationChannel(
            CHANNEL_ID, CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        val operateTimerText = if(timerOperator.isStart){
            "stop"
        }else{
            "start"
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .addAction(R.drawable.ic_launcher_foreground, operateTimerText, operateTimerPendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "close", closeTimerPendingIntent)

        return notification.build()
    }
}

fun startNotifyForegroundService(context: Context) {
    ContextCompat.startForegroundService(context, Intent(context, VariableNotificationService::class.java))
}

fun stopNotifyForegroundService(context: Context) {
    val targetIntent = Intent(context, VariableNotificationService::class.java)
    context.stopService(targetIntent)
}