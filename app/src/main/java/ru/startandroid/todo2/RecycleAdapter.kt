package ru.startandroid.todo

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.todo.Task.Task
import ru.startandroid.todo.extensions.inflate
import ru.startandroid.todo2.R
import ru.startandroid.todo2.Task.ActionListener
import ru.startandroid.todo2.converters.DateConverter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapter(listener: ActionListener) : RecyclerView.Adapter<RecyclerAdapter.TaskHolder>()  {

    public var selectItem: Int = -1
    private var tasks: List<Task> = ArrayList()
    private var mlistener: ActionListener=listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        return TaskHolder(parent.inflate(R.layout.recyclerview_item_row))
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(tasks[position], mlistener);

    }

    fun refreshTasks(newTask: List<Task>) {
        Log.d("MyLog","refresh")
        tasks=newTask
        notifyDataSetChanged()

    }

    inner class TaskHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private var textOfTask: TextView? = v.findViewById(R.id.task_text)
        private var completeTask: RadioButton = v.findViewById(R.id.complete)
        private var dateOfTask: TextView = v.findViewById(R.id.dateString)

        //3
        init {
           view.setOnClickListener(this)
            completeTask.setOnClickListener {
                selectItem= tasks[layoutPosition].id!!
                mlistener.onRadioButtonClick(selectItem)
            }
        }

        fun bind(task: Task, listener: ActionListener) {
            textOfTask?.setText(task.name);
            var cal=DateConverter.fromLongToDate(task.date)
            dateOfTask?.text=DateConverter.fromDateToString(cal)
        }

        //4
        override fun onClick(v: View) {
            Log.d("MyLog", "id:    "+tasks[layoutPosition].id.toString())
            var bundle = bundleOf("id" to tasks[layoutPosition].id)
            view.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)





        }
    }}
