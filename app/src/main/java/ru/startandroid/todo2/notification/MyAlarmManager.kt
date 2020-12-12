package ru.startandroid.todo2.notification

import android.content.Context

class MyAlarmManager(context: Context) {
    private var alarm: AlarmManagerBroadcastReceiver =
        AlarmManagerBroadcastReceiver()
    private var mContext = context

    fun startRepeatingTimer(text: String) {
        var context: Context = mContext
        if (alarm != null) {
            alarm.setAlarm(context,text)
        }
    }

    fun cancelTimer() {
        var context: Context = mContext
        if (alarm != null) {
            alarm.cancelAlarm(context)
        }
    }

    fun onetimeTimer(text: String, time : Long) {
        var context: Context = mContext
        if (alarm != null) {
            alarm.setOnetimeTimer(context,text, time)
        }
    }
}