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
    //获取ToDo数据
    fun getToDoData(): LiveData<List<ToDo>> {
        return toDoDao.getToDoData()
    }
    //删除一条ToDo
    suspend fun deleteToDoData(toDo: ToDo) {
        toDoDao.deleteToDoData(toDo)
    }
    //删除所有ToDo
    suspend fun deleteAllToDoData() {
        toDoDao.deleteAllToDoData()
    }
    //更新数据
    suspend fun updateToDoData(toDo: ToDo) {
        toDoDao.updateToDoData(toDo)
    }

    //-------- 操作tagDao --------
    //插入标签
    suspend fun insertTag(tagData: TagData) {
        tagDao.insertTag(tagData)
    }
    //读取所有标签
    fun getAllTags(): LiveData<List<TagData>> {
        return tagDao.getAllTags()
    }
    //删除标签
    suspend fun deleteTag(tagData: TagData) {
        tagDao.deleteTag(tagData)
    }

}