package ru.startandroid.todo2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_first.*
import ru.startandroid.todo.RecyclerAdapter
import ru.startandroid.todo2.Task.TaskViewModelFactory
import ru.startandroid.todo2.Task.TasksViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val taskViewModel by lazy {
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

        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        recyclerAdapter = RecyclerAdapter()
        recyclerView.adapter = recyclerAdapter

        taskViewModel.getListTasks().observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerAdapter.refreshTasks(it)
            }
        })
    }
}
