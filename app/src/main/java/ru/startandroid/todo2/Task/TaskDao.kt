package ru.startandroid.todo.Task

import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * from tasks")
    suspend fun getAllTasks(): List<Task>
}