package com.mayberry.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import com.mayberry.todolist.data.model.Date
import com.mayberry.todolist.data.model.Priority
import com.mayberry.todolist.data.model.Tag
import com.mayberry.todolist.data.model.ToDo
import com.mayberry.todolist.databinding.ActivityMainBinding
import com.mayberry.todolist.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val data = ToDo(0, "android class", "android开发课程，不要迟到",
                Priority.HIGH, Date(2023,6,11), Tag("Android", "#666"))
            mViewModel.insertToDoData(data)
        }
        return true
    }
}