package ru.startandroid.todo2.converters

import android.util.Log
import androidx.navigation.Navigator
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class TimeConverter {
    private val timeFormat= SimpleDateFormat("hh:mm")

    @TypeConverter
    fun toCalendar(time : String): Calendar{

        val date = timeFormat.parse(time)
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    @TypeConverter
    fun toString(time: Calendar) : String{
        Log.d("MyLog","8")
            return timeFormat.format(time.time)
    }
}