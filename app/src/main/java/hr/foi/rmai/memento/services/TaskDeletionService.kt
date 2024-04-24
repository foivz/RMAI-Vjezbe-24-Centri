package hr.foi.rmai.memento.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import hr.foi.rmai.memento.database.TasksDAO
import hr.foi.rmai.memento.database.TasksDatabase

class TaskDeletionService : Service() {

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        TasksDatabase.buildInstance(applicationContext)
        val taskDao = TasksDatabase.getInstance().getTasksDao()

        taskDao.getAllTasks(true).forEach {task ->
            if (task.isOverdue()) {
                taskDao.removeTask(task)
            }
        }

        return START_REDELIVER_INTENT
    }
}