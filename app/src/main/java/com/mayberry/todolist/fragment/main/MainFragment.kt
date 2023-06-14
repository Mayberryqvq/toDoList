package com.mayberry.todolist.fragment.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mayberry.todolist.R
import com.mayberry.todolist.data.model.Priority
import com.mayberry.todolist.data.model.ToDo
import com.mayberry.todolist.databinding.FragmentMainBinding
import com.mayberry.todolist.showToast

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter: ToDoAdapter by lazy { ToDoAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        mainViewModel.toDoDataList.observe(viewLifecycleOwner) {
            checkStatus(it)
            if (it.isNotEmpty()) {
                initTopToDo(it[0])
            }
            if (it.size > 1) {
                mAdapter.setData(it.subList(1, it.size))
            } else {
                mAdapter.setData(emptyList())
            }
        }
        binding.addBtn.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment()
            findNavController().navigate(action)
        }
        binding.topTaskContainer.setOnClickListener {
            val data = mainViewModel.toDoDataList.value!![0]
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(data)
            findNavController().navigate(action)
        }
        binding.deleteAllBtn.setOnClickListener {
            mainViewModel.deleteAllToDoData()
            showToast(requireContext(), "Delete all succeeded")
        }
    }

    private fun checkStatus(dataList: List<ToDo>) {
        if (dataList.isNotEmpty()) {
            binding.joyfulFace.visibility = View.VISIBLE
            binding.joyfulText.visibility = View.VISIBLE
            binding.topTaskContainer.visibility = View.INVISIBLE
            binding.deleteAllBtn.visibility = View.INVISIBLE
        } else {
            binding.joyfulFace.visibility = View.INVISIBLE
            binding.joyfulText.visibility = View.INVISIBLE
            binding.topTaskContainer.visibility = View.VISIBLE
            binding.deleteAllBtn.visibility = View.VISIBLE
        }
    }

    private fun initTopToDo(toDo: ToDo) {
        binding.topTitle.text = toDo.title
        binding.topTime.text = "${toDo.date.year}.${toDo.date.month + 1}.${toDo.date.day}"
        binding.topDescription.text = toDo.description
        binding.topTag.text = toDo.tag.name
        binding.topTag.background.setTint(Color.parseColor(toDo.tag.bgColor))
        when (toDo.priority) {
            Priority.HIGH -> binding.topPriority.setImageResource(R.drawable.red_ball)
            Priority.MIDDLE -> binding.topPriority.setImageResource(R.drawable.yellow_ball)
            Priority.LOW -> binding.topPriority.setImageResource(R.drawable.green)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        swipeToDelete()
    }

    private fun swipeToDelete() {
        val touchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val index = viewHolder.adapterPosition
                val data = mainViewModel.toDoDataList.value!![index + 1]
                mainViewModel.deleteToDoData(data)
                showToast(requireContext(), "Delete have finished!")
            }
        })
        touchHelper.attachToRecyclerView(binding.recyclerView)
    }

}