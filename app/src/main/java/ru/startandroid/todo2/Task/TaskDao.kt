package ru.startandroid.todo.Task

import androidx.room.*
import ru.startandroid.todo2.converters.DateConverter
import java.util.*


@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("DELETE from tasks WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * from tasks order by date")
    suspend fun getAllTasks(): List<Task>

    @Query("SELECT * from tasks WHERE id = :id")
    suspend fun getTaskById(id: Int): Task

    @Query("SELECT * FROM tasks WHERE date BETWEEN :dateFrom and :dateTo")
    @TypeConverters(DateConverter::class)
    suspend fun getByDate( dateFrom: Date, dateTo : Date): List<Task>
}