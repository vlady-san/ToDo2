package ru.startandroid.todo

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject
import ru.startandroid.todo.Task.Task
import ru.startandroid.todo.extensions.inflate
import ru.startandroid.todo2.R
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapter(private val tasks: List<Task>)  : RecyclerView.Adapter<RecyclerAdapter.TaskHolder>()  {

//    private val clickSubject = PublishSubject.create<Task>()
//    val clickEvent: Observable<Task> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        return TaskHolder(parent.inflate(R.layout.recyclerview_item_row))
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(tasks[position]);
    }

    inner class TaskHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private var textOfTask: TextView? = null
        private val clickSubject = PublishSubject.create<List<Task>>()

        //3
        init {
            view.setOnClickListener {
                //clickSubject.onNext (tasks[layoutPosition])
            }
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
