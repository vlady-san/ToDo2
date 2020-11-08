package ru.startandroid.todo2.Task

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaskViewModelFactory(context: Context?) : ViewModelProvider.Factory {
    var mContext: Context? = context

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return mContext?.let { TasksViewModel(it) } as T
    }
}