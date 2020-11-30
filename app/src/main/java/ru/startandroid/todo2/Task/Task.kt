package ru.startandroid.todo.Task

import androidx.annotation.NonNull
import androidx.room.*
import ru.startandroid.todo2.SubTask.SubTask
import ru.startandroid.todo2.converters.DateConverter
import ru.startandroid.todo2.converters.TimeConverter
import java.util.*

@Entity(tableName = "tasks")
data class Task(@PrimaryKey(autoGenerate = true) val id: Int? = null,
                @ColumnInfo(name = "name") var name: String,
                @ColumnInfo(name = "description") var description: String,
                @ColumnInfo(name = "date") var date: Long,
                @ColumnInfo(name = "remind") val timeRemind: String
) {
}