package hr.foi.rmai.memento.helpers

import hr.foi.rmai.memento.entities.Task
import hr.foi.rmai.memento.entities.TaskCategory
import java.util.Date

object MockDataLoader {
    fun getDemoCategories() : List<TaskCategory> = listOf(
        TaskCategory(1, "EP", "#000000"),
        TaskCategory(2, "SIS", "#FF0000"),
        TaskCategory(3, "RMAI", "#000080"),
        TaskCategory(4, "OS", "#CCCCCC")
    )

    fun getDemoData() : MutableList<Task> {
        val categories = getDemoCategories()

        return mutableListOf(
            Task(1, "Submit seminar paper", Date(), 1, false),
            Task(2, "Prepare for excerises", Date(), 1, false),
            Task(3, "Rally a project name", Date(), 2, false),
            Task(4, "Connect to server", Date(), 3, false))
    }
}