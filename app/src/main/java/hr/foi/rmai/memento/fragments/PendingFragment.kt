package hr.foi.rmai.memento.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.foi.rmai.memento.R
import hr.foi.rmai.memento.adapters.TasksAdapter
import hr.foi.rmai.memento.database.TasksDatabase
import hr.foi.rmai.memento.helpers.NewTaskDialogHelper

class PendingFragment : Fragment() {
    //private val mockTasks = MockDataLoader.getDemoData()
    private lateinit var recyclerView : RecyclerView
    private lateinit var btnCreateTask : FloatingActionButton
    private val tasksDao = TasksDatabase.getInstance().getTasksDao()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_pending_tasks)

        loadTaskList()

        btnCreateTask = view.findViewById(R.id.btn_create_task)
        btnCreateTask.setOnClickListener {
            showDialog()
        }
    }

    private fun loadTaskList() {
        val tasks = tasksDao.getAllTasks(false)

        for (task in tasks) {
            Log.e("TAG", task.id.toString())
        }

        recyclerView.adapter = TasksAdapter(tasks.toMutableList()) { taskId ->
            parentFragmentManager.setFragmentResult(
                "task_completed",
                bundleOf("task_id" to taskId.toLong())
            )
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun showDialog() {
        val newTaskDialogView = LayoutInflater.from(context).inflate(R.layout.new_task_dialog, null)

        val dialogHelper = NewTaskDialogHelper(newTaskDialogView)
        val categories = TasksDatabase.getInstance().getTaskCategoriesDao().getAllCategories()
        dialogHelper.populateSpinner(categories)

        dialogHelper.activateDateTimeListener()

        AlertDialog.Builder(context)
            .setView(newTaskDialogView)
            .setTitle("Create a new task")
            .setPositiveButton("Create a new task") { _,_ ->
                var newTask = dialogHelper.buildTask()
                val newTaskId = tasksDao.insertTask(newTask)[0]
                newTask = tasksDao.getTask(newTaskId)

                val tasksAdapter = recyclerView.adapter as TasksAdapter
                tasksAdapter.addTask(newTask)

                incrementTasksCreatedCounter()
            }
            .show()
    }

    private fun incrementTasksCreatedCounter() {
        context?.getSharedPreferences("tasks_preferences", Context.MODE_PRIVATE)?.apply {
            val currentCount = getInt("tasks_created_counter", 0)
            edit().putInt("tasks_created_counter", currentCount + 1).apply()
        }
    }
}

