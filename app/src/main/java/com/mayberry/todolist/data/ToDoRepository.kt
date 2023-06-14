package com.mayberry.todolist.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.mayberry.todolist.data.model.TagData
import com.mayberry.todolist.data.model.ToDo

class ToDoRepository(context: Context) {

    private val toDoDao = ToDoDatabase.getDatabase(context).getToDoDao()
    private val tagDao = ToDoDatabase.getDatabase(context).getTagDao()

    //-------- 操作toDoDao --------
    suspend fun insertToDoData(toDo: ToDo) {
        toDoDao.insertToDoData(toDo)
    }

    fun getToDoData(): LiveData<List<ToDo>> {
        return toDoDao.getToDoData()
    }

    suspend fun deleteToDoData(toDo: ToDo) {
        toDoDao.deleteToDoData(toDo)
    }

    suspend fun deleteAllToDoData() {
        toDoDao.deleteAllToDoData()
    }

    suspend fun updateToDoData(toDo: ToDo) {
        toDoDao.updateToDoData(toDo)
    }

    //-------- 操作tagDao --------
    suspend fun insertTag(tagData: TagData) {
        tagDao.insertTag(tagData)
    }

    fun getAllTags(): LiveData<List<TagData>> {
        return tagDao.getAllTags()
    }

    suspend fun deleteTag(tagData: TagData) {
        tagDao.deleteTag(tagData)
    }

}