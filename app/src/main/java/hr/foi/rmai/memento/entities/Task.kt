package hr.foi.rmai.memento.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import hr.foi.rmai.memento.converters.DateConverter
import hr.foi.rmai.memento.database.TasksDatabase
import java.util.Calendar
import java.util.Date

@Entity(tableName="tasks",
    foreignKeys = [
       ForeignKey(
           entity = TaskCategory::class,
           parentColumns = ["id"],
           childColumns = ["category_id"],
           onDelete = ForeignKey.RESTRICT
       )
    ])
@TypeConverters(DateConverter::class)
data class Task(
    @PrimaryKey(autoGenerate = true) val id : Long,
    val name : String,
    @ColumnInfo(name="due_date")
    val dueDate : Date,
    @ColumnInfo(name="category_id", index = true)
    val categoryId : Long,
    var completed : Boolean
) {
    @delegate:Ignore
    val category: TaskCategory by lazy {
        TasksDatabase
            .getInstance()
            .getTaskCategoriesDao()
            .getCategoryById(categoryId)
    }

    fun isOverdue(): Boolean {
        val dateFromMonthAgo = Calendar.getInstance().apply {
            add(Calendar.DATE, -30)
        }.time

        return dueDate.before(dateFromMonthAgo)
    }
}
