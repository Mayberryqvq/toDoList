package com.mayberry.todolist.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDo (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priority,
    @Embedded
    val date: Date,
    @Embedded
    val tag: Tag
)

enum class Priority{HIGH, LOW, MIDDLE}

data class Date(val year: Int, val month: Int, val day: Int)

data class Tag(val name: String, val bgColor: String)

