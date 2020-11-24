package ru.startandroid.todo2.converters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
    companion object {
        val dateFormat = SimpleDateFormat("dd/MM/yy")
        fun fromStringToDate(date: String): Calendar {

            val date = dateFormat.parse(date)
            val cal = Calendar.getInstance()
            cal.time = date
            return cal
        }

        fun fromLongToDate(date: Long): Calendar {
            val date = Date(date)
            val cal = Calendar.getInstance()
            cal.time = date
            return cal
        }

        fun fromDateToString(date: Calendar): String {
            return dateFormat.format(date.time)
        }
    }
}