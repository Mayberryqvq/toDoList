package com.mayberry.todolist

import androidx.room.TypeConverter
import com.mayberry.todolist.data.model.Priority

class ToDoConverter {

    @TypeConverter
    fun priorityToString(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun stringToPriority(str: String): Priority {
        return Priority.valueOf(str)
    }
}