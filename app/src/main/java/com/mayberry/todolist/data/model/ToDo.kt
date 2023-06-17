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
    //内容
    var description: String,
    //事务的重要性
    var priority: Priority,
    //管理日期
    @Embedded
    var date: Date,
    ///管理标签
    @Embedded
    var tag: Tag
): Parcelable

/** 标识事务的重要性 **/
enum class Priority{ HIGH, LOW, MIDDLE }

/** 管理日期 **/
@Parcelize
data class Date(var year: Int, var month: Int, var day: Int): Parcelable

/** 管理标签 **/
@Parcelize
data class Tag(val name: String, val bgColor: String): Parcelable

