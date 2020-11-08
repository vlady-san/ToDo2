package ru.startandroid.todo

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.todo.Task.Task
import ru.startandroid.todo.extensions.inflate
import ru.startandroid.todo2.R
import kotlin.collections.ArrayList

class RecyclerAdapter()  : RecyclerView.Adapter<RecyclerAdapter.TaskHolder>()  {

    private var tasks: List<Task> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        return TaskHolder(parent.inflate(R.layout.recyclerview_item_row))
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(tasks[position]);
    }

    fun refreshTasks(newTask: List<Task>) {
        tasks=newTask
        notifyDataSetChanged()

    }

    inner class TaskHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private var textOfTask: TextView? = null

        //3
        init {
           view.setOnClickListener(this)
            textOfTask = v?.findViewById(R.id.task_text)
        }

        fun bind(task: Task) {
            textOfTask?.setText(task.name);
        }

        //4
        override fun onClick(v: View) {
            Log.d("MyLog", layoutPosition.toString())
            var bundle = bundleOf("isSave" to 0, "position" to layoutPosition)
            view.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)

        }
    }}
