package com.whatziya.todo.data.source.local

import com.whatziya.todo.data.db.ToDoEntity
import kotlinx.coroutines.flow.Flow

interface ToDoLocalDataSource {
    fun getAllItems(): Flow<List<ToDoEntity>>
    fun getItemById(id: String): Flow<ToDoEntity>
    suspend fun insertItem(item: ToDoEntity)
    suspend fun updateItem(item: ToDoEntity)
    suspend fun deleteItem(item: ToDoEntity)
    suspend fun deleteItemById(id: String)
}