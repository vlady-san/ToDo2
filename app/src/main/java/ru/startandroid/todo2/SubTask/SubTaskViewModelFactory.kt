package ru.startandroid.todo2.SubTask

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SubTaskViewModelFactory(application: Application, idTask: Int) : ViewModelProvider.Factory {
    var mIdTask: Int = idTask
    val mApplication: Application = application

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SubTaskViewModel(mApplication,mIdTask) as T
    }
}