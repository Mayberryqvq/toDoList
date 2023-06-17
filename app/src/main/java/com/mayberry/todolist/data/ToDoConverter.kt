package com.mayberry.todolist.data

import androidx.room.TypeConverter
import com.mayberry.todolist.data.model.Priority

class ToDoConverter {
    //将Priority对象存入数据库时调用
    @TypeConverter
    fun priorityToString(priority: Priority): String {
        return priority.name
    }

    //从数据库里面读取出来之后转化为Priority对象
    @TypeConverter
    fun stringToPriority(str: String): Priority {
        return Priority.valueOf(str)
    }
}