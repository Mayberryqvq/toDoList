package com.mayberry.todolist.fragment.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mayberry.todolist.data.ToDoRepository
import com.mayberry.todolist.data.model.TagData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ToDoRepository(application)
    var tagList:LiveData<List<TagData>> = repository.getAllTags()

    fun insertTag(tagData: TagData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTag(tagData)
        }
    }

}