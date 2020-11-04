package ru.startandroid.todo.Task

import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * from tasks")
    fun getAllTasks(): List<Task>

    @Query("SELECT COUNT(*) from tasks")
    fun getSize(): Int
}