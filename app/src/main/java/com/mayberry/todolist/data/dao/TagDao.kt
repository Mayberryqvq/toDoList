package com.mayberry.todolist.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mayberry.todolist.data.model.TagData

@Dao
interface TagDao {
    ///插入标签
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tagData: TagData)

    //读取所有标签
    @Query("select * from tag_table")
    fun getAllTags(): LiveData<List<TagData>>

    //删除标签
    @Delete
    suspend fun deleteTag(tagData: TagData)
}