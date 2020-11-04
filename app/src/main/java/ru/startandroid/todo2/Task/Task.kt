package ru.startandroid.todo.Task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.properties.Delegates

@Entity(tableName = "tasks")
data class Task(@PrimaryKey(autoGenerate = true) val id: Int? = null,
           @ColumnInfo(name = "name") val name: String,
           @ColumnInfo(name = "description") val description: String,
           @ColumnInfo(name = "creation") val dataCreation: String) {
}