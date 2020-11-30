package ru.startandroid.todo2.SubTask

import androidx.room.*


@Dao
interface SubTaskDao {

    @Query("SELECT * FROM subtask WHERE id_task = :id_task")
    fun getByIdUser(id_task: Int): List<SubTask>?

    @Query("SELECT * FROM subtask WHERE id = :id")
    fun getById(id: Int): SubTask?

    @Insert
    fun insert(subTask: SubTask)

    @Update
    fun update(subTask: SubTask)

    @Query("DELETE FROM subtask WHERE id = :id")
    fun delete(id: Int)
}