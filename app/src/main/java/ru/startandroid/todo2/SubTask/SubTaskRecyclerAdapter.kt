package ru.startandroid.todo2.SubTask

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.todo.extensions.inflate
import ru.startandroid.todo2.R
import ru.startandroid.todo2.Task.ActionListener

class SubTaskRecyclerAdapter(listener: ActionListener) : RecyclerView.Adapter<SubTaskRecyclerAdapter.SubTaskHolder>() {

    private var subTasks: List<SubTask> = ArrayList()
    private var mListener: ActionListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubTaskHolder {
        return SubTaskHolder(
            parent.inflate(R.layout.subtask_recycler_view), mListener
        )
    }

    override fun getItemCount(): Int {
        return subTasks.size
    }

    override fun onBindViewHolder(holder: SubTaskHolder, position: Int) {
        holder.bind(subTasks[position]);
    }
    fun refreshTasks(newSubTasks: List<SubTask>) {
        Log.d("MyLog","refresh")
        subTasks=newSubTasks
        notifyDataSetChanged()

    }
    class SubTaskHolder(v: View, listener: ActionListener) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var textOfTask: TextView? = view.findViewById(R.id.et_subtask)
        private var radioButton : RadioButton? = view.findViewById(R.id.rb_subtask)
        private var mListener: ActionListener=listener

        init {

        }


        fun bind(subTask: SubTask) {
            textOfTask?.text = subTask.name
            radioButton?.setOnClickListener(View.OnClickListener {
                mListener.onRadioButtonClick(subTask.id!!)
                radioButton?.isChecked=false
            })
        }

    }
}