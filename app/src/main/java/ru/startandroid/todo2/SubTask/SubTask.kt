package ru.startandroid.todo2.SubTask

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import ru.startandroid.todo.Task.Task

@Entity(foreignKeys = arrayOf(
    ForeignKey(
        entity = Task::class,
                parentColumns = arrayOf("id"),
        childColumns = arrayOf("id_task"),
        onDelete = CASCADE
    )
)
)
class SubTask(@PrimaryKey(autoGenerate = true) val id: Int? = null,
              var name: String,
              val id_task: Int) {
}