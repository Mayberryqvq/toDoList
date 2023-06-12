package com.mayberry.todolist.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mayberry.todolist.data.model.ToDo
import com.mayberry.todolist.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.toDoDataList.observe(viewLifecycleOwner) {
            checkStatus(it)
        }
    }

    private fun checkStatus(dataList: List<ToDo>) {
        if (dataList.isNotEmpty()) {
            binding.joyfulFace.visibility = View.VISIBLE
            binding.joyfulText.visibility = View.VISIBLE
        } else {
            binding.joyfulFace.visibility = View.INVISIBLE
            binding.joyfulText.visibility = View.INVISIBLE
        }
    }

}