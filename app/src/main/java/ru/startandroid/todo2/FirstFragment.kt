package ru.startandroid.todo2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import ru.startandroid.todo.RecyclerAdapter
import ru.startandroid.todo.Task.Task
import ru.startandroid.todo.TasksDataBase

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var subscribe: Disposable?=null
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerAdapter: RecyclerAdapter
    private var db: TasksDataBase? = null
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = activity?.let { TasksDataBase.getDatabase(context = it) }

        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        var tasks = db?.taskDao()?.getAllTasks()
        recyclerAdapter= RecyclerAdapter(tasks as ArrayList<Task>)
        recyclerView.adapter = recyclerAdapter

        //subscribe=recyclerAdapter.clickEvent.subscribe{

        //}
//        fab.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onResume() {
        super.onResume()
        var tasks = db?.taskDao()?.getAllTasks()
        recyclerAdapter= RecyclerAdapter(tasks as ArrayList<Task>)
        recyclerAdapter.notifyDataSetChanged()

    }
}