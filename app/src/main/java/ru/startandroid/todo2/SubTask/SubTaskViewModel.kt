package ru.startandroid.todo2.SubTask

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.startandroid.todo.Task.Task
import ru.startandroid.todo.Task.TaskDao
import ru.startandroid.todo.TasksDataBase

class SubTaskViewModel(application: Application, idTask: Int) : AndroidViewModel(application) {

    private var db: TasksDataBase? = null
    private var subTaskDao: SubTaskDao?=null
    private var subTaskList : MutableLiveData<List<SubTask>> = MutableLiveData()
    private val mIdTask:Int=idTask

    init {
        db = TasksDataBase.getDatabase(application)
        subTaskDao = db?.subTaskDao()
        GlobalScope.launch {
            subTaskList.postValue(subTaskDao?.getByIdUser(idTask))
        }
    }

    fun getListSubTask() = subTaskList

    //для обновления списка
    fun updateListUsers() {
        GlobalScope.launch {
            subTaskList.postValue(subTaskDao?.getByIdUser(mIdTask))
        }
    }

    fun insertSubTask(subTask: SubTask){
        GlobalScope.launch {
            subTaskDao?.insert(subTask)
            updateListUsers()
        }
    }

    fun updateSubTask(subTask: SubTask){
        GlobalScope.launch {
            subTaskDao?.update(subTask)
        }
    }

    fun deleteSubTask(id: Int){
        GlobalScope.launch {
            subTaskDao?.delete(id)
            updateListUsers()
        }
    }

    fun getSubTaskForTask(idTask: Int){
        GlobalScope.launch {
            var subTask = subTaskDao?.getByIdUser(idTask)
        }
    }


}