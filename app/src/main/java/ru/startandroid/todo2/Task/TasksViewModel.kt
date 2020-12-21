package ru.startandroid.todo2.Task

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
    var taskList : MutableLiveData<List<Task>> = MutableLiveData()
    init {
        db = TasksDataBase.getDatabase(context)
        taskDao = db?.taskDao()
        viewModelScope.launch(Dispatchers.IO) {
        taskList.postValue(taskDao?.getAllTasks())
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
        viewModelScope.launch(Dispatchers.IO) {
            taskList.postValue(taskDao?.getAllTasks())
        }
    }

    fun updateNameTask(id: Int, newTask: Task){
        viewModelScope.launch(Dispatchers.IO) {
            var task = taskDao?.getTaskById(id)
            if (task != null) {
                task.name = newTask.name
                task.description=newTask.description
                task.date=newTask.date
                with(taskDao) {
                    this?.update(task)
                }
            }
        }
    }

    fun setDateForTask(date: Calendar, id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            var task= taskDao?.getTaskById(id)
            if (task != null) {
                task.date=date.time.time
                taskDao?.insert(task)
            }
        }
    }

    fun insertTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
        with(taskDao) {
            this?.insert(task)
        }
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao?.deleteById(id)
            println(taskDao?.getAllTasks()?.size.toString())
        }
    }

    fun getTaskByDate(dateFrom : Calendar){
        var dateTo=Calendar.getInstance()
        dateTo.time=dateFrom.time
        dateTo.add(Calendar.DAY_OF_MONTH,1)
        System.out.println(dateTo.time.toString())

        viewModelScope.launch(Dispatchers.IO) {
            System.out.println(taskDao?.getByDate(dateFrom.time,dateTo.time)?.size)
            taskList.postValue(taskDao?.getByDate(dateFrom.time,dateTo.time))

        }
    }

}