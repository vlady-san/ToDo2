package ru.startandroid.todo2.Task

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.startandroid.todo.Task.Task
import ru.startandroid.todo.Task.TaskDao
import ru.startandroid.todo.TasksDataBase
import ru.startandroid.todo2.SubTask.SubTask
import ru.startandroid.todo2.SubTask.SubTaskDao
import java.util.*

class TasksViewModel(context: Context) : ViewModel() {
    private var db: TasksDataBase? = null
    private var taskDao: TaskDao? = null
    private var subTaskDao: SubTaskDao?=null
    var taskList : MutableLiveData<List<Task>> = MutableLiveData()
    var subTaskList : MutableLiveData<List<SubTask>> = MutableLiveData()

    init {
        Log.d("MyLog", "init")
        db = TasksDataBase.getDatabase(context)
        taskDao = db?.taskDao()
        GlobalScope.launch {
        taskList.postValue(taskDao?.getAllTasks())
            Log.d("MyLog", "load")
        }
    }
    fun getListTasks(): MutableLiveData<List<Task>> {
        Log.d("MyLog", "get")
        return taskList
    }

    suspend fun getTaskById(id: Int): Task? {
        return taskDao?.getTaskById(id)
    }

    fun updateListTasks() {
        GlobalScope.launch {
            taskList.postValue(taskDao?.getAllTasks())
        }
    }

    fun updateNameTask(id: Int, newTask: Task){
        GlobalScope.launch {
            var task = taskDao?.getTaskById(id)
            if (task != null) {
                task.name = newTask.name
                task.description=newTask.description
                task.date=newTask.date
                with(taskDao) {
                    this?.insert(task)
                }
            }
        }
    }

    fun setDateForTask(date: Calendar, id: Int){
        GlobalScope.launch {
            var task= taskDao?.getTaskById(id)
            if (task != null) {
                task.date=date.time.time
                taskDao?.insert(task)
            }
        }
    }

    fun insertTask(task: Task){
        GlobalScope.launch {
        with(taskDao) {
            this?.insert(task)
        }
        }
    }

    fun deleteTask(id: Int) {
        GlobalScope.launch {
            taskDao?.deleteById(id)
            println(taskDao?.getAllTasks()?.size.toString())
        }
        //updateListTasks()
    }

    fun getTaskByDate(dateFrom : Calendar){
        var dateTo=Calendar.getInstance()
        dateTo.time=dateFrom.time
        dateTo.add(Calendar.DAY_OF_MONTH,1)

        GlobalScope.launch {

            taskList.postValue(taskDao?.getByDate(dateFrom.time,dateTo.time))
        }
    }

}