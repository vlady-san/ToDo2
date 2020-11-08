package ru.startandroid.todo2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_second.*
import ru.startandroid.todo.Task.TaskDao
import ru.startandroid.todo.TasksDataBase
import ru.startandroid.todo2.Task.TaskViewModelFactory
import ru.startandroid.todo2.Task.TasksViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private val taskViewModel by lazy { ViewModelProvider(this, TaskViewModelFactory(context)).get(TasksViewModel::class.java)}
    private var isSave : Int? =null
    private var position : Int?=null
    private var db: TasksDataBase? = null
    private var genderDao: TaskDao? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        position = arguments?.getInt("position")
        isSave = arguments?.getInt("isSave")

        btn.setOnClickListener(View.OnClickListener {
            taskViewModel.insertOrUpdateTask(position,isSave,et_task.text.toString())
            taskViewModel.updateListUsers()
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        })

        db = activity?.let { TasksDataBase.getDatabase(context = it) }
        genderDao = db?.taskDao()
    }
}