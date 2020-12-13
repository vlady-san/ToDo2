package ru.startandroid.todo2

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_first.*
import ru.startandroid.todo.RecyclerAdapter
import ru.startandroid.todo2.Task.ActionListener
import ru.startandroid.todo2.Task.TaskViewModelFactory
import ru.startandroid.todo2.Task.TasksViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), ActionListener {
    private val taskViewModel by lazy{
        ViewModelProvider(this, TaskViewModelFactory(context)).get(
            TasksViewModel::class.java
        )
    }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerAdapter: RecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //добавление задачи
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            activity?.let {
                Navigation.findNavController(
                    it,
                    R.id.nav_host_fragment
                ).navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }



        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        recyclerAdapter = RecyclerAdapter(this)
        recyclerView.adapter = recyclerAdapter

        taskViewModel?.getListTasks()?.observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerAdapter.refreshTasks(it)
            }
        })

        var dateStr=arguments?.getString("date")
        if(dateStr!=null){
            val dateFormat = SimpleDateFormat("dd/MM/yy")
            val date = dateFormat.parse(dateStr)
            val cal = Calendar.getInstance()
            cal.time = date
            getTaskByDate(cal)
        }


    }

    override fun onRadioButtonClick(id: Int) {
        taskViewModel.deleteTask(id)
        Toast.makeText(context,"Задача выполнена", Toast.LENGTH_SHORT).show()
        taskViewModel.updateListTasks()

    }

    private fun getTaskByDate(dateFrom: Calendar){
        taskViewModel.getTaskByDate(dateFrom)
    }
}
