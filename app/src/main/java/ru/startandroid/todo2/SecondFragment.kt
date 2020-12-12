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
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_second.*
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
class SecondFragment : Fragment(), ActionListener {

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
    private var mcontext: Context?=null
    private var am:MyAlarmManager?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        am= MyAlarmManager(requireContext())


        // установка обработчика выбора времени
        val t =
            OnTimeSetListener { view, hourOfDay, minute ->



                GlobalScope.launch {
                    var task = tasksViewModel.getTaskById(mId)
                    if (task != null) {
                        dateAndTime.time=Date(task.date)
                    }
                    dateAndTime[Calendar.HOUR_OF_DAY] = hourOfDay
                    dateAndTime[Calendar.MINUTE] = minute
                    dateAndTime[Calendar.SECOND]=0
                    if (task != null) {
                        am!!.onetimeTimer(task.name, dateAndTime.time.time)
                    }
                    else am!!.onetimeTimer(et_task_name.text.toString(), dateAndTime.time.time)
                    Log.d("MyLog", dateAndTime.time.toString() + et_task_name)

                }
            }
        // установка обработчика выбора даты
        val d =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                dateAndTime[Calendar.YEAR] = year
                dateAndTime[Calendar.MONTH] = monthOfYear
                dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth
                dateAndTime[Calendar.HOUR_OF_DAY] = 0
                dateAndTime[Calendar.MINUTE] = 0
                Log.d("MyLog", dateAndTime.time.time.toString() + "d")
                tasksViewModel.setDateForTask(dateAndTime, mId)
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

        var linearLayoutManager = LinearLayoutManager(activity)
        sub_task_recyclerView.layoutManager = linearLayoutManager
        val adapter = SubTaskRecyclerAdapter(this)
        sub_task_recyclerView.adapter = adapter

        Log.d("MyLog", "id" + arguments?.getInt("id"))
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

        //радиобатон для выполнения
        complete.setOnClickListener {
            tasksViewModel.deleteTask(mId)
            tasksViewModel.updateListTasks()
            //am.c(et_task_name.text.toString(), dateAndTime.time.time)
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)

        }

        //сохранить
        save_task.setOnClickListener(View.OnClickListener {

            var task = Task(
                name = et_task_name.text.toString(),
                description = et_task_description.text.toString(),
                date = dateAndTime.time.time,
                timeRemind = "calendar"
            )
            if (mId == -1)
                tasksViewModel.insertTask(task)
            else
                tasksViewModel.updateNameTask(mId, task)
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            mId = -1
        })

        //добавить подзадачу
        add_sub_task.setOnClickListener(View.OnClickListener {
            var nameSubTask:String
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
        })


    }

    override fun onRadioButtonClick(id: Int) {
        subTaskViewModel.deleteSubTask(id)
        Toast.makeText(activity, "Подзадача завершена", Toast.LENGTH_SHORT).show()
    }
}
