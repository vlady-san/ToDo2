package ru.startandroid.todo.Task

import androidx.room.*

@Entity(tableName = "tasks")
data class Task(@PrimaryKey(autoGenerate = true) val id: Int? = null,
                @ColumnInfo(name = "name") var name: String,
                @ColumnInfo(name = "description") var description: String,
                @ColumnInfo(name = "date") var date: Long
) {
}