package com.mayberry.todolist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag_table")
data class TagData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val bgColor: String
)