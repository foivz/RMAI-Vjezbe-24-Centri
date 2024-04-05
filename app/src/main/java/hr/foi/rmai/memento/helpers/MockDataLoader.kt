package hr.foi.rmai.memento.helpers

import hr.foi.rmai.memento.database.TasksDatabase
import hr.foi.rmai.memento.entities.Task
import hr.foi.rmai.memento.entities.TaskCategory
import java.util.Date

object MockDataLoader {
    fun loadMockData() {
        val tasksDao = TasksDatabase.getInstance().getTasksDao()
        val taskCategoriesDao = TasksDatabase.getInstance().getTaskCategoriesDao()

        if (tasksDao.getAllTasks(false).isEmpty()
            && tasksDao.getAllTasks(true).isEmpty()
            && taskCategoriesDao.getAllCategories().isEmpty())
        {
            val categories = arrayOf(
                TaskCategory(1, "EP", "#000000"),
                TaskCategory(2, "SIS", "#FF0000"),
                TaskCategory(3, "RMAI", "#000080"),
                TaskCategory(4, "OS", "#CCCCCC")
            )
            taskCategoriesDao.insertCategory(*categories)

            val dbCategories = taskCategoriesDao.getAllCategories()
            val tasks = arrayOf(
                Task(1, "Submit seminar paper", Date(), dbCategories[0].id, false),
                Task(2, "Prepare for excerises", Date(), dbCategories[1].id, false),
                Task(3, "Rally a project name", Date(), dbCategories[1].id, false),
                Task(4, "Connect to server", Date(), dbCategories[2].id, false))
            tasksDao.insertTask(*tasks)
        }
    }
}