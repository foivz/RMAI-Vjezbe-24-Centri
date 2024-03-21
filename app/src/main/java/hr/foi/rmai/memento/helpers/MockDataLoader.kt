package hr.foi.rmai.memento.helpers

import hr.foi.rmai.memento.entities.Task
import hr.foi.rmai.memento.entities.TaskCategory
import java.util.Date

object MockDataLoader {
    fun getDemoData() : List<Task> = listOf(
        Task("Submit seminar paper", Date(),
            TaskCategory("EP", "#000000"), false),

        Task("Prepare for excerises", Date(),
            TaskCategory("SIS", "#FF0000"), false),

        Task("Rally a project name", Date(),
            TaskCategory("RMAI", "#000080"), false),

        Task("Connect to server", Date(),
            TaskCategory("OS", "#CCCCCC"), false)
    )
}