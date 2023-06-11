package com.mayberry.todolist.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mayberry.todolist.data.model.ToDo

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDoData(toDo: ToDo)

    @Query("select * from todo_table")
    fun getToDoData(): LiveData<List<ToDo>>

    @Delete
    suspend fun deleteToDoData(toDo: ToDo)

    @Query("delete from todo_table")
    fun deleteAllToDoData()

}