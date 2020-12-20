package ru.startandroid.todo2

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.fragment_second.view.*
import kotlinx.android.synthetic.main.sub_task_add_dialog.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.startandroid.todo.Task.Task
import ru.startandroid.todo2.SubTask.SubTask
import ru.startandroid.todo2.SubTask.SubTaskRecyclerAdapter
import ru.startandroid.todo2.SubTask.SubTaskViewModel
import ru.startandroid.todo2.SubTask.SubTaskViewModelFactory
import ru.startandroid.todo2.Task.ActionListener
import ru.startandroid.todo2.Task.TaskViewModelFactory
import ru.startandroid.todo2.Task.TasksViewModel
import ru.startandroid.todo2.notification.MyAlarmManager
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), ActionListener, OnTimeSetListener,
    OnDateSetListener, View.OnClickListener {

    private val tasksViewModel by lazy {
        ViewModelProvider(this, TaskViewModelFactory(context)).get(
            TasksViewModel::class.java
        )
    }
    private val subTaskViewModel by lazy{
        ViewModelProviders.of(this,
            activity?.application?.let { SubTaskViewModelFactory(it, mId)})
            .get(SubTaskViewModel::class.java)}

    private var mId: Int = -1
    private var dateAndTime: Calendar = Calendar.getInstance()
    private var am:MyAlarmManager?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        am= MyAlarmManager(requireContext())

        date.setOnClickListener(this)
        time.setOnClickListener(this)
        complete.setOnClickListener (this)
        save_task.setOnClickListener(this)
        add_sub_task.setOnClickListener(this)

        var linearLayoutManager = LinearLayoutManager(activity)
        sub_task_recyclerView.layoutManager = linearLayoutManager
        val adapter = SubTaskRecyclerAdapter(this)
        sub_task_recyclerView.adapter = adapter


        arguments?.getInt("id")?.let {
            mId = it
            GlobalScope.launch {
                var task = tasksViewModel.getTaskById(mId)
                if (task != null) {
                    et_task_name.setText(task.name)
                    et_task_description.setText(task.description)
                }
            }
            subTaskViewModel.getListSubTask().observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.refreshTasks(it)
                }
            })
        }
        if(mId==-1)
            add_sub_task.visibility=View.GONE
        else add_sub_task.visibility=View.VISIBLE
    }


    override fun onRadioButtonClick(id: Int) {
        subTaskViewModel.deleteSubTask(id)
        Toast.makeText(activity, "Подзадача завершена", Toast.LENGTH_SHORT).show()
    }

    //обработка нвыбора времени
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        GlobalScope.launch {
            var task = tasksViewModel.getTaskById(mId)
            if (task != null) {
                dateAndTime.time = Date(task.date)
            }
            dateAndTime[Calendar.HOUR_OF_DAY] = hourOfDay
            dateAndTime[Calendar.MINUTE] = minute
            dateAndTime[Calendar.SECOND] = 0
            if (task != null) {
                am!!.onetimeTimer(task.name, dateAndTime.time.time)
            } else am!!.onetimeTimer(et_task_name.text.toString(), dateAndTime.time.time)
        }
    }

    //обработка выбора даты
    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        dateAndTime[Calendar.YEAR] = year
        dateAndTime[Calendar.MONTH] = monthOfYear
        dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth
        dateAndTime[Calendar.HOUR_OF_DAY] = 0
        dateAndTime[Calendar.MINUTE] = 0
        tasksViewModel.setDateForTask(dateAndTime, mId)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when(view.id){
                //показ диалога времени
                R.id.date -> {
                    context?.let {
                        DatePickerDialog(
                            it, this,
                            dateAndTime.get(Calendar.YEAR),
                            dateAndTime.get(Calendar.MONTH),
                            dateAndTime.get(Calendar.DAY_OF_MONTH)
                        )
                            .show()
                    }
                }
                //показ диалога времени
                R.id.time -> {
                    TimePickerDialog(
                        context, this,
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE), true
                    )
                        .show()
                }
                //радиокнопка завершения задачи
                R.id.complete ->{
                    tasksViewModel.deleteTask(mId)
                    tasksViewModel.updateListTasks()
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)

                }
                //сохранение задачи
                R.id.save_task ->{

                    var task = Task(
                        name = et_task_name.text.toString(),
                        description = et_task_description.text.toString(),
                        date = dateAndTime.time.time
                    )
                    if (mId == -1)
                        tasksViewModel.insertTask(task)
                    else
                        tasksViewModel.updateNameTask(mId, task)
                    //findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                    activity?.onBackPressed()

                    mId = -1
                }
                //добавление подзадачи
                R.id.add_sub_task ->{
                    val mDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
                    val mDialogView = LayoutInflater.from(context).inflate(R.layout.sub_task_add_dialog, null)
                    mDialogBuilder.setView(mDialogView)
                    mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(
                            "Ок"
                        ) { dialog, id -> //Вводим текст и отображаем в строке ввода на основном экране:
                            val nameSubTask = mDialogView.findViewById<EditText>(R.id.sub_task_text).text.toString()
                            subTaskViewModel.insertSubTask(SubTask(name = nameSubTask, id_task = mId))
                        }
                        .setNegativeButton(
                            "Отмена"
                        ) { dialog, id -> dialog.cancel() }
                    val alertDialog = mDialogBuilder.create()
                    alertDialog.show()

                    scroll_view.fullScroll(ScrollView.FOCUS_DOWN)
                }
            }
        }
    }
}
