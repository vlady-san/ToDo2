package ru.startandroid.todo2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_second.*
import ru.startandroid.todo.Task.Task
import ru.startandroid.todo.Task.TaskDao
import ru.startandroid.todo.TasksDataBase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

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

            var task : Task?=null
            if(isSave==1)
                task = Task(id= db?.taskDao()?.getSize()?.plus(1),name=et_task.text.toString(),description="dd",dataCreation="dd")
            else
            {
                var listTasks = db?.taskDao()?.getAllTasks()
                task=Task(id= position?.let { it1 -> listTasks?.get(it1)?.id },name=et_task.text.toString(),description="dd",dataCreation="dd")
            }
            with(genderDao){

                this?.insert(task)
            }

            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)

        })

        db = activity?.let { TasksDataBase.getDatabase(context = it) }
        genderDao = db?.taskDao()
    }
}