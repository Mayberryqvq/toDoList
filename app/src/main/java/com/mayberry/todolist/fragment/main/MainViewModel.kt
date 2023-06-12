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
    private val repository = ToDoRepository(application)
    val toDoDataList: LiveData<List<ToDo>> = repository.getToDoData()

    fun insertToDoData(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertToDoData(toDo)
        }
    }

    fun deleteToDoData(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteToDoData(toDo)
        }
    }

    fun deleteAllToDoData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllToDoData()
        }
    }
}