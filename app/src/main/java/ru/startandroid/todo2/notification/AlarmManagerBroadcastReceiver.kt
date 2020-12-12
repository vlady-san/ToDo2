package ru.startandroid.todo2.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import java.lang.Boolean


class AlarmManagerBroadcastReceiver: BroadcastReceiver() {
    val TEXT_TASK :String="text_task"
    val ONE_TIME :String="onetime"
    var nNotification: MyNotification?=null
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
           var pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            var wakeLock=pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AppName:Tag")
            nNotification = MyNotification(context)

            //осуществляет блокировку
            wakeLock.acquire()
            var extras = intent!!.extras
            var text = extras!!.getString(TEXT_TASK)
            nNotification?.notifyMyNotification(text!!)
            //здесть можно делать обработку


            wakeLock.release();
        }

    }

    fun setAlarm(context: Context, text: String){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmManagerBroadcastReceiver::class.java)
        intent.putExtra(ONE_TIME,false)
        intent.putExtra(TEXT_TASK, text)
        val pendingIntent=PendingIntent.getBroadcast(context,0,intent,0)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),5000,pendingIntent)
    }
    fun cancelAlarm(context: Context) {
        val intent = Intent(context, AlarmManagerBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent) //Отменяем будильник, связанный с интентом данного класса
    }

    fun setOnetimeTimer(context: Context, text: String, time: Long) {
        var am =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(context, AlarmManagerBroadcastReceiver::class.java)
        //intent.putExtra(ONE_TIME, Boolean.TRUE) //Задаем параметр интента
        intent.putExtra(TEXT_TASK, text)
        var pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    }
}