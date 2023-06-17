package com.mayberry.todolist.fragment.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mayberry.todolist.data.ToDoRepository
import com.mayberry.todolist.data.model.ToDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    //数据仓库
    private val repository = ToDoRepository(application)
    //ToDo数据
    val toDoDataList: LiveData<List<ToDo>> = repository.getToDoData()

    fun insertToDoData(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertToDoData(toDo)
        }
    }

    //删除一条ToDo
    fun deleteToDoData(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteToDoData(toDo)
        }
    }

    //删除所有ToDo
    fun deleteAllToDoData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllToDoData()
        }
    }

    fun updateToDoData(toDo: ToDo) {
        viewModelScope.launch {
            repository.updateToDoData(toDo)
        }
    }
}