package com.example.variablenotification

import android.app.Service
import android.content.Intent
import android.os.IBinder

class CloseTimerService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        stopNotifyForegroundService(applicationContext)

        return START_NOT_STICKY
    }
}