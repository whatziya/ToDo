package com.whatziya.todo.domain.usecase.todo

import com.whatziya.todo.data.repository.ToDoRepository
import com.whatziya.todo.domain.mapper.ToDoLocalMapper
import com.whatziya.todo.domain.ui_model.ToDoUIModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalEditorUseCase @Inject constructor(
    private val repository: ToDoRepository,
    private val mapper: ToDoLocalMapper
){
    fun getItemById(id: String) = repository.getItemById(id).map {
        mapper.toUIModel(it)
    }

    suspend fun insertItem(item: ToDoUIModel) = repository.insertItem(mapper.toDTO(item))

    suspend fun updateItem(item: ToDoUIModel) = repository.updateItem(mapper.toDTO(item))

    suspend fun deleteItem(item: ToDoUIModel) = repository.deleteItem(mapper.toDTO(item))
}