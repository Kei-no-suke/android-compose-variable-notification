package com.example.variablenotification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StopTimerService: Service() {
    @Inject lateinit var timerOperator: TimerOperator
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timerOperator.timerStop()

        return START_NOT_STICKY
    }
}