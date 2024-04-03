package hr.foi.rmai.memento.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.foi.rmai.memento.R
import hr.foi.rmai.memento.adapters.TasksAdapter
import hr.foi.rmai.memento.helpers.MockDataLoader
import hr.foi.rmai.memento.helpers.NewTaskDialogHelper

class PendingFragment : Fragment() {
    private val mockTasks = MockDataLoader.getDemoData()
    private lateinit var recyclerView : RecyclerView
    private lateinit var btnCreateTask : FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mockTasks.forEach { task ->
            Log.i("MOCK_PENDING_TASKS", task.name)
        }

        return inflater.inflate(R.layout.fragment_pending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_pending_tasks)
        recyclerView.adapter = TasksAdapter(mockTasks)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        btnCreateTask = view.findViewById(R.id.btn_create_task)
        btnCreateTask.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val newTaskDialogView = LayoutInflater.from(context).inflate(R.layout.new_task_dialog, null)

        val dialogHelper = NewTaskDialogHelper(newTaskDialogView)
        dialogHelper.populateSpinner(MockDataLoader.getDemoCategories())

        dialogHelper.activateDateTimeListener()

        AlertDialog.Builder(context)
            .setView(newTaskDialogView)
            .setTitle("Create a new task")
            .setPositiveButton("Create a new task") { _,_ ->
                val newTask = dialogHelper.buildTask()
            }
            .show()
    }
}

