package com.example.variablenotification

import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerOperator @Inject constructor() {
    var timerSeconds: Long = 0
    private var timer: Timer? = null
    var isStart: Boolean = false

    fun setTimer(seconds: Long) {
        timerSeconds = seconds
    }

    fun timerStart() {
        isStart = true
        if(timer != null){
            timer!!.cancel()
            timer = null
        }
        timer = Timer()
        timer!!.scheduleAtFixedRate(object: TimerTask(){
            override fun run() {
                if(timerSeconds > 0){
                    timerSeconds--
                }
            }
        },0,1000)
    }

    fun timerStop() {
        isStart = false
        if(timer != null){
            timer!!.cancel()
            timer = null
        }
    }
}