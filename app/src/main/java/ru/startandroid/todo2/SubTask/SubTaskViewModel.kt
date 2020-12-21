package ru.startandroid.todo2.SubTask

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
        viewModelScope.launch(Dispatchers.IO) {
            subTaskList.postValue(subTaskDao?.getByIdUser(idTask))
        }
    }

    fun getListSubTask() = subTaskList

    //для обновления списка
    fun updateListUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            subTaskList.postValue(subTaskDao?.getByIdUser(mIdTask))
        }
    }

    fun insertSubTask(subTask: SubTask){
        viewModelScope.launch(Dispatchers.IO) {
            subTaskDao?.insert(subTask)
            updateListUsers()
        }
    }

    fun updateSubTask(subTask: SubTask){
        viewModelScope.launch(Dispatchers.IO) {
            subTaskDao?.update(subTask)
        }
    }

    fun deleteSubTask(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            subTaskDao?.delete(id)
            updateListUsers()
        }
    }

    fun getSubTaskForTask(idTask: Int){
        viewModelScope.launch(Dispatchers.IO) {
            var subTask = subTaskDao?.getByIdUser(idTask)
        }
    }


}