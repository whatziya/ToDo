package com.whatziya.todo.data.repository

import com.whatziya.todo.data.db.ToDoEntity
import com.whatziya.todo.data.source.local.ToDoLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val localDataSource: ToDoLocalDataSource
) : ToDoRepository {
    override fun getAllItems(): Flow<List<ToDoEntity>> {
        return localDataSource.getAllItems()
    }

    override fun getItemById(id: String): Flow<ToDoEntity> {
        return localDataSource.getItemById(id)
    }

    override suspend fun insertItem(item: ToDoEntity) {
        return localDataSource.insertItem(item)
    }

    override suspend fun updateItem(item: ToDoEntity) {
        return localDataSource.updateItem(item)
    }

    override suspend fun deleteItem(item: ToDoEntity) {
        return localDataSource.deleteItem(item)
    }

    override suspend fun deleteItemById(id: String) {
        return localDataSource.deleteItemById(id)
    }

}