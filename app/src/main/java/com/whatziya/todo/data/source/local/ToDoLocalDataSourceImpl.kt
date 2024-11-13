package com.whatziya.todo.data.source.local

import com.whatziya.todo.data.db.ToDoDao
import com.whatziya.todo.data.dto.ToDoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class ToDoLocalDataSourceImpl @Inject constructor(
    private val todoDao: ToDoDao
) : ToDoLocalDataSource {
    override fun getAllItems(): Flow<List<ToDoEntity>> {
        return todoDao.getAllItems()
    }

    override fun getItemById(id: String): Flow<ToDoEntity> {
        return todoDao.getItemById(id)
    }

    override suspend fun insertItem(item: ToDoEntity) {
        return todoDao.insertItem(item)
    }

    override suspend fun updateItem(item: ToDoEntity) {
        return todoDao.updateItem(item)
    }

    override suspend fun deleteItem(item: ToDoEntity) {
        return todoDao.deleteItem(item)
    }

    override suspend fun deleteItemById(id: String) {
        return todoDao.deleteItemById(id)
    }

}