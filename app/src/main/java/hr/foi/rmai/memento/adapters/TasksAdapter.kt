package hr.foi.rmai.memento.adapters

import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rmai.memento.R
import hr.foi.rmai.memento.entities.Task
import java.text.SimpleDateFormat

class TasksAdapter(val tasksList : List<Task>) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val taskName: TextView
        private val taskDueDate: TextView
        private val taskCategory: SurfaceView

        private val sdf: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")

        init {
            taskName = view.findViewById(R.id.tv_task_name)
            taskDueDate = view.findViewById(R.id.tv_task_due_date)
            taskCategory = view.findViewById(R.id.sv_task_category_color)
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
}