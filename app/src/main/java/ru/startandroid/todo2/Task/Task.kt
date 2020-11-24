package ru.startandroid.todo.Task

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.startandroid.todo2.converters.DateConverter
import ru.startandroid.todo2.converters.TimeConverter
import java.util.*

@Entity(tableName = "tasks")
data class Task(@PrimaryKey(autoGenerate = true) val id: Int? = null,
                @ColumnInfo(name = "name") var name: String,
                @ColumnInfo(name = "description") var description: String,
                @ColumnInfo(name = "date") var date: Long,
                @ColumnInfo(name = "remind") val timeRemind: String) {
}