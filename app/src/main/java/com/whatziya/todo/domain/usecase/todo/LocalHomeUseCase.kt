package com.whatziya.todo.domain.usecase.todo

import com.whatziya.todo.data.repository.ToDoRepository
import com.whatziya.todo.domain.mapper.ToDoLocalMapper
import com.whatziya.todo.domain.ui_model.ToDoUIModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalHomeUseCase @Inject constructor(
    private val repository: ToDoRepository,
    private val mapper: ToDoLocalMapper
) {
    fun getAllItems() = repository.getAllItems().map {
        it.map { item ->
            mapper.toUIModel(item)
        }
    }

    suspend fun updateItem(item: ToDoUIModel) = repository.updateItem(mapper.toDTO(item))
    suspend fun deleteItemById(id: String) = repository.deleteItemById(id)
}