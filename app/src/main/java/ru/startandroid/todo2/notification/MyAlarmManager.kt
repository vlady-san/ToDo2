package ru.startandroid.todo2.notification

import android.content.Context

class MyAlarmManager(context: Context) {
    private var alarm: AlarmManagerBroadcastReceiver =
        AlarmManagerBroadcastReceiver()
    private val mContext = context

    fun startRepeatingTimer(text: String) {
        val context: Context = mContext
        if (alarm != null) {
            alarm.setAlarm(context,text)
        }
    }

    fun cancelRepeatingTimer() {
        val context: Context = mContext
        if (alarm != null) {
            alarm.cancelAlarm(context)
        }
    }

    fun onetimeTimer(text: String) {
        val context: Context = mContext
        if (alarm != null) {
            alarm.setOnetimeTimer(context,text)
        }
    }
}