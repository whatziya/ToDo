package com.whatziya.todo.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_entity")
data class ToDoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : String,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "importance")
    val importance : Int,
    @ColumnInfo(name = "deadline")
    val deadline : Long,
    @ColumnInfo(name = "is_completed")
    val isCompleted : Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt : Long,
    @ColumnInfo(name = "modified_at")
    val modifiedAt : Long
)