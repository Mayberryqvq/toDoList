package com.mayberry.todolist.fragment.main

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mayberry.todolist.R
import com.mayberry.todolist.data.model.Priority
import com.mayberry.todolist.data.model.ToDo

class ToDoAdapter: RecyclerView.Adapter<ToDoAdapter.MyViewHolder>() {

    private var dataList: List<ToDo> = emptyList()

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var dateTextView: TextView = itemView.findViewById(R.id.date)
        private var priorityImageView: ImageView = itemView.findViewById(R.id.priority)
        private var titleTextView: TextView = itemView.findViewById(R.id.toDoTitle)
        private var timeTextView: TextView = itemView.findViewById(R.id.time)
        private var tagTextView: TextView = itemView.findViewById(R.id.tag)

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.todo_item_layout, parent, false)
                return MyViewHolder(view)
            }
        }

        @SuppressLint("setTextI18n")
        fun bindData(toDo: ToDo) {
            titleTextView.text = toDo.title
            tagTextView.text = toDo.tag.name
            tagTextView.backgroundTintList = ColorStateList.valueOf(Color.parseColor(toDo.tag.bgColor))
            when(toDo.priority) {
                Priority.HIGH -> priorityImageView.setImageResource(R.drawable.red_plate)
                Priority.MIDDLE -> priorityImageView.setImageResource(R.drawable.yellow_plate)
                Priority.LOW -> priorityImageView.setImageResource(R.drawable.green_plate)
            }
            dateTextView.text = "${toDo.date.month + 1}.${toDo.date.day}"
            itemView.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(toDo)
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (dataList.isNotEmpty()) {
            holder.bindData(dataList[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    
    fun setData(newData: List<ToDo>) {
        val diffResult = DiffUtil.calculateDiff(ToDoDiffUtil(newData, dataList))
        dataList = newData
        diffResult.dispatchUpdatesTo(this)
    }
    
}