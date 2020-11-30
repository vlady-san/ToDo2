package ru.startandroid.todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.startandroid.todo.Task.Task
import ru.startandroid.todo.Task.TaskDao
import ru.startandroid.todo2.SubTask.SubTask
import ru.startandroid.todo2.SubTask.SubTaskDao

@Database(entities = arrayOf(Task::class, SubTask::class), version = 1, exportSchema = false)
public abstract class TasksDataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun subTaskDao(): SubTaskDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TasksDataBase? = null

        fun getDatabase(context: Context): TasksDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TasksDataBase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

