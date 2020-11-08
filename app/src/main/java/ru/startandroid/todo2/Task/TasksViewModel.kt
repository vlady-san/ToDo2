package ru.startandroid.todo2.Task

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.startandroid.todo.Task.Task
import ru.startandroid.todo.Task.TaskDao
import ru.startandroid.todo.TasksDataBase

class TasksViewModel(context: Context) : ViewModel() {
    private var db: TasksDataBase? = null
    private var genderDao: TaskDao? = null
    var taskList : MutableLiveData<List<Task>> = MutableLiveData()

    init {
        db = TasksDataBase.getDatabase(context)
        genderDao = db?.taskDao()
        GlobalScope.launch {
        taskList.postValue(genderDao?.getAllTasks())
        }
    }
    fun getListTasks() = taskList

    fun updateListUsers() {
        GlobalScope.launch {
            taskList.postValue(genderDao?.getAllTasks())
        }
    }

    fun updateTask(position: Int, text: String){
        var task: Task? = null
        task = Task(
            id = position?.let { it1 -> taskList.value?.get(it1)?.id },
            name = text,
            description = "dd",
            dataCreation = "dd"
        )
        GlobalScope.launch {
            with(genderDao) {
                this?.insert(task)
            }
        }
    }

    fun insertTask(text: String){
        var task: Task? = null
        task = Task(
            name = text,
            description = "dd",
            dataCreation = "dd"
        )
        GlobalScope.launch {
        with(genderDao) {
            this?.insert(task)
        }
        }
    }

    fun insertOrUpdateTask(position: Int?, isInsert: Int?, text: String){
            if (isInsert == 1)
                insertTask(text)
            else position?.let { updateTask(it,text) }
        }

}