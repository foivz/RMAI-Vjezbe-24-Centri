package hr.foi.rmai.memento.database

import androidx.room.Database
import hr.foi.rmai.memento.entities.Task
import hr.foi.rmai.memento.entities.TaskCategory

@Database(version = 1, entities = [Task::class, TaskCategory::class])
abstract class TasksDatabase {
    abstract fun getTasksDao(): TasksDAO
    abstract fun getTaskCategoriesDao(): TaskCategoriesDAO

    companion object {

    }
}