package hr.foi.rmai.memento.helpers

import hr.foi.rmai.memento.entities.Task
import hr.foi.rmai.memento.entities.TaskCategory
import java.util.Date

object MockDataLoader {
    fun getDemoCategories() : List<TaskCategory> = listOf(
        TaskCategory("EP", "#000000"),
        TaskCategory("SIS", "#FF0000"),
        TaskCategory("RMAI", "#000080"),
        TaskCategory("OS", "#CCCCCC")
    )

    fun getDemoData() : MutableList<Task> {
        val categories = getDemoCategories()

        return mutableListOf(
            Task("Submit seminar paper", Date(), categories[0], false),
            Task("Prepare for excerises", Date(), categories[0], false),
            Task("Rally a project name", Date(), categories[2], false),
            Task("Connect to server", Date(), categories[3], false))
    }
}