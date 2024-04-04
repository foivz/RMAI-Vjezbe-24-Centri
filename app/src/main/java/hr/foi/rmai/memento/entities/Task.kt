package hr.foi.rmai.memento.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import hr.foi.rmai.memento.converters.DateConverter
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
data class Task(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val name : String,
    @ColumnInfo(name="due_date")
    @TypeConverters(DateConverter::class)
    val dueDate : Date,
    @ColumnInfo(name="category_id", index = true)
    val categoryId : Int,
    val completed : Boolean
) {
    lateinit var category: TaskCategory
}
