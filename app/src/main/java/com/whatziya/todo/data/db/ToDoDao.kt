package com.whatziya.todo.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.whatziya.todo.data.dto.ToDoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo_entity")
    fun getAllItems(): Flow<List<ToDoEntity>>

    @Query("SELECT * FROM todo_entity WHERE id=:id")
    fun getItemById(id: String): Flow<ToDoEntity>

    @Insert
    suspend fun insertItem(item: ToDoEntity)

    @Update(entity = ToDoEntity::class)
    suspend fun updateItem(item: ToDoEntity)

    @Delete
    suspend fun deleteItem(item: ToDoEntity)

    @Query("DELETE FROM todo_entity WHERE id = :id")
    suspend fun deleteItemById(id: String)
}