package com.mayberry.todolist.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mayberry.todolist.data.model.TagData

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tagData: TagData)

    @Query("select * from tag_table")
    fun getAllTags(): LiveData<List<TagData>>

    @Delete
    suspend fun deleteTag(tagData: TagData)

}