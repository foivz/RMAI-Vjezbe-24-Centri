package hr.foi.rmai.memento.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_categories")
data class TaskCategory(
    @PrimaryKey(autoGenerate = true) val id : Long,
    val name : String,
    val color : String) {
    override fun toString(): String {
        return name
    }
}
