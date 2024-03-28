package hr.foi.rmai.memento.helpers

import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import hr.foi.rmai.memento.R
import hr.foi.rmai.memento.entities.Task
import hr.foi.rmai.memento.entities.TaskCategory
import java.util.Calendar

class NewTaskDialogHelper(val view:View) {
    private val spinner = view.findViewById<Spinner>(R.id.spn_new_task_dialog_category)
    private val dateSelection = view.findViewById<EditText>(R.id.et_new_task_dialog_date)
    private val timeSelection = view.findViewById<EditText>(R.id.et_new_task_dialog_time)

    private val selectedDateTime: Calendar = Calendar.getInstance()

    fun populateSpinner(categories : List<TaskCategory>) {
        val spinnerAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
    }

    fun buildTask() : Task {
        val etName = view.findViewById<EditText>(R.id.et_new_task_dialog_name)
        val newTaskName = etName.text.toString()
        val selectedCategory = spinner.selectedItem as TaskCategory

        return Task(newTaskName, selectedDateTime.time, selectedCategory, false)
    }
}