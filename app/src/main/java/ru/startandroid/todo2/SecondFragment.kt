package ru.startandroid.todo2

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.startandroid.todo.Task.Task
import ru.startandroid.todo2.Task.TaskViewModelFactory
import ru.startandroid.todo2.Task.TasksViewModel
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(){

    private val tasksViewModel by lazy { ViewModelProvider(this, TaskViewModelFactory(context)).get(
        TasksViewModel::class.java)}
    private var mId : Int = -1
    private var dateAndTime : Calendar = Calendar.getInstance();

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // установка обработчика выбора времени
        val t =
            OnTimeSetListener { view, hourOfDay, minute ->
                dateAndTime[Calendar.HOUR_OF_DAY] = hourOfDay
                dateAndTime[Calendar.MINUTE] = minute
                Log.d("MyLog",dateAndTime.time.toString()+"!")
            }
        // установка обработчика выбора даты
        val d =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                dateAndTime[Calendar.YEAR] = year
                dateAndTime[Calendar.MONTH] = monthOfYear
                dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth
                dateAndTime[Calendar.HOUR_OF_DAY] = 0
                dateAndTime[Calendar.MINUTE] = 0
                Log.d("MyLog",dateAndTime.time.time.toString()+"d")
                tasksViewModel.setDateForTask(dateAndTime,mId)
            }

        //диалог даты
        view.findViewById<ImageButton>(R.id.date).setOnClickListener(View.OnClickListener {
            context?.let {
                DatePickerDialog(
                    it, d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH)
                )
                    .show()
            }
        })
        //диалог времени
        view.findViewById<ImageButton>(R.id.time).setOnClickListener(View.OnClickListener {
            TimePickerDialog(
                context, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true
            )
                .show()
        })
        Log.d("MyLog","id"+ arguments?.getInt("id"))
        arguments?.getInt("id")?.let { mId=it
            GlobalScope.launch {
                var task= tasksViewModel.getTaskById(mId)
                if (task != null) {
                    et_task_name.setText(task.name)
                    et_task_description.setText(task.description)
                }
            }
        }

        //радиобатон для выполнения
        complete.setOnClickListener {
            tasksViewModel.deleteTask(mId)
            tasksViewModel.updateListTasks()
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        //сохранить
        btn.setOnClickListener(View.OnClickListener {

            var task= Task(
                name = et_task_name.text.toString(),
                description = et_task_description.text.toString(),
                date = dateAndTime.time.time,
                timeRemind = "calendar")
            if(mId==-1)
                tasksViewModel.insertTask(task)
            else
                tasksViewModel.updateNameTask(mId,task)
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            mId=-1
        })



}
}