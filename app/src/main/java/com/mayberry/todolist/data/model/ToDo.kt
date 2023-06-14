package com.mayberry.todolist.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "todo_table")
data class ToDo (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var description: String,
    var priority: Priority,
    @Embedded
    var date: Date,
    @Embedded
    var tag: Tag
): Parcelable

enum class Priority{ HIGH, LOW, MIDDLE }

@Parcelize
data class Date(var year: Int, var month: Int, var day: Int): Parcelable

@Parcelize
data class Tag(val name: String, val bgColor: String): Parcelable

