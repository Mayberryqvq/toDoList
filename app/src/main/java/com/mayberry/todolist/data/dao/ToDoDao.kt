package com.mayberry.todolist.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mayberry.todolist.data.model.ToDo

@Dao
interface ToDoDao {
    //插入toDo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDoData(toDo: ToDo)
    //获取toDo数据
    @Query("select * from todo_table")
    fun getToDoData(): LiveData<List<ToDo>>
    //删除指定toDo
    @Delete
    suspend fun deleteToDoData(toDo: ToDo)
    //删除所有toDo
    @Query("delete from todo_table")
    suspend fun deleteAllToDoData()
    //更新数据
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateToDoData(toDo: ToDo)
}