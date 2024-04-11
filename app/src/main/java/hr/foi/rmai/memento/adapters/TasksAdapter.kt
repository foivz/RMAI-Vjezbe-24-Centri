package hr.foi.rmai.memento.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rmai.memento.R
import hr.foi.rmai.memento.database.TasksDatabase
import hr.foi.rmai.memento.entities.Task
import java.text.SimpleDateFormat

class TasksAdapter(val tasksList : MutableList<Task>,
                   val onTaskCompleted: ((taskId: Int) -> Unit)? = null
                   ) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val taskName: TextView
        private val taskDueDate: TextView
        private val taskCategory: SurfaceView

        private val sdf: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")

        init {
            taskName = view.findViewById(R.id.tv_task_name)
            taskDueDate = view.findViewById(R.id.tv_task_due_date)
            taskCategory = view.findViewById(R.id.sv_task_category_color)

            view.setOnLongClickListener {
                val currentTask = tasksList[adapterPosition]

                val alertDialogBuilder = AlertDialog.Builder(view.context)
                    .setNegativeButton("Delete task") { _, _ ->
                        TasksDatabase.getInstance().getTasksDao().removeTask(currentTask)
                        removeTaskFromList()
                    }
                    .setNeutralButton("Cancel") { dialog, _ ->
                        dialog.cancel()
                    }


                if (onTaskCompleted != null) {
                    alertDialogBuilder.setPositiveButton("Mark as completed") { _, _ ->
                        currentTask.completed = true
                        TasksDatabase.getInstance().getTasksDao().insertTask(currentTask)
                        removeTaskFromList()

                        onTaskCompleted.invoke(currentTask.id)
                    }
                }

                alertDialogBuilder.show()

                true
            }
        }

        private fun removeTaskFromList() {
            tasksList.removeAt(adapterPosition)
            notifyItemRemoved(adapterPosition)
        }

        fun bind(task: Task) {
            taskName.text = task.name
            taskDueDate.text = sdf.format(task.dueDate)
            taskCategory.setBackgroundColor(task.category.color.toColorInt())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val taskView = LayoutInflater.from(parent.context).inflate(
            R.layout.task_list_item, parent, false)

        return TaskViewHolder(taskView)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasksList[position])
    }

    fun addTask(newTask: Task) {
        var newIndexInList = tasksList.indexOfFirst {
            task -> task.dueDate > newTask.dueDate
        }

        if (newIndexInList == -1) {
            newIndexInList = tasksList.size
        }

        tasksList.add(newIndexInList, newTask)
        notifyItemInserted(newIndexInList)
    }
}