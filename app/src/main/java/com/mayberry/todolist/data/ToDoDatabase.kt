package com.mayberry.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mayberry.todolist.data.dao.TagDao
import com.mayberry.todolist.data.dao.ToDoDao
import com.mayberry.todolist.data.model.TagData
import com.mayberry.todolist.data.model.ToDo

@TypeConverters(ToDoConverter::class)
@Database(entities = [ToDo::class, TagData::class], version = 1, exportSchema = false)
abstract class ToDoDatabase: RoomDatabase() {

    abstract fun getToDoDao(): ToDoDao

    abstract fun getTagDao(): TagDao

    //创建单例对象
    companion object {
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {
            if (INSTANCE != null) return INSTANCE!!
            //创建对象
            synchronized(this) {
                if (INSTANCE == null) INSTANCE = Room.databaseBuilder(context, ToDoDatabase::class.java, "todo.db").build()
            }
            return INSTANCE!!
        }
    }

}