package hr.foi.rmai.memento.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import hr.foi.rmai.memento.R
import hr.foi.rmai.memento.database.TasksDatabase
import hr.foi.rmai.memento.entities.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Date

class TaskTimerService : Service() {
    private val tasks = mutableListOf<Task>()
    private var started: Boolean = false
    private val NOTIFICATION_ID = 10000

    private val mutex = Mutex()
    private var scope: CoroutineScope? = null

    override fun onBind(intent: Intent): IBinder? = null

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val taskId = intent!!.getLongExtra("task_id", -1)
        val isCancelled = intent!!.getBooleanExtra("cancel", false)

        TasksDatabase.buildInstance(applicationContext)
        val task = TasksDatabase.getInstance().getTasksDao().getTask(taskId)

        Log.e("RMAI", taskId.toString())

        if (tasks.contains(task)) {
            if (isCancelled) {
                tasks.remove(task)
            }
        } else if (task.dueDate > Date()){
            tasks.add(task)

            if (!started) {
                val notification = buildTimerNotification("")
                startForeground(NOTIFICATION_ID, notification)
                scope = CoroutineScope(Dispatchers.Main)
                scope!!.launch {
                    displayNotifications()
                    stopForeground(true)
                    started = false
                }

                started = true
            }
        }

        return START_NOT_STICKY
    }

    @SuppressLint("MissingPermission")
    private suspend fun displayNotifications() {
        val sb = StringBuilder()
        var taskToBeDeleted: Task? = null

        while (tasks.isNotEmpty()) {
            mutex.withLock {
                for (task in tasks) {
                    val remainingMilliseconds = task.dueDate.time - Date().time

                    if (remainingMilliseconds <= 0) {
                        taskToBeDeleted = task
                    } else {
                        sb.appendLine(task.name + ": " + remainingMilliseconds)
                    }
                }

                NotificationManagerCompat.from(applicationContext)
                    .notify(NOTIFICATION_ID, buildTimerNotification(sb.toString()))
            }

            if (taskToBeDeleted != null) {
                mutex.withLock {
                    tasks.remove(taskToBeDeleted)
                }
            }

            delay(1000)
        }
    }

    private fun buildTimerNotification(contextText: String): Notification {
        return NotificationCompat.Builder(applicationContext, "task-timer")
            .setContentTitle("Task countdown")
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contextText))
            .setOnlyAlertOnce(true)
            .build()
    }

    override fun onDestroy() {
        started = false
        scope?.apply {
            if (isActive) cancel()
        }
    }
}