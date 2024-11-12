package com.whatziya.todo.views.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whatziya.todo.domain.ui_model.ToDoUIModel
import com.whatziya.todo.domain.usecase.todo.LocalHomeUseCase
import com.whatziya.todo.preferences.PreferencesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefsProvider: PreferencesProvider,
    private val useCase: LocalHomeUseCase
) : ViewModel() {

    private val _toDoItems = MutableStateFlow<List<ToDoUIModel>>(emptyList())
    val toDoItems: StateFlow<List<ToDoUIModel>> get() = _toDoItems

    init {
        viewModelScope.launch {
            useCase.getAllItems()
                .collect { toDoEntities ->
                    if(prefsProvider.hideCompleted){
                        _toDoItems.value = toDoEntities.filter { !it.isCompleted }
                    } else {
                        _toDoItems.value = toDoEntities
                    }
                }
        }
    }

    fun updateItem(item: ToDoUIModel) = viewModelScope.launch {
        useCase.updateItem(item)
    }

    fun deleteItemById(id: String) = viewModelScope.launch {
        useCase.deleteItemById(id)
    }

    fun setHideCompleted(hideCompleted: Boolean) = viewModelScope.launch {
        prefsProvider.hideCompleted = hideCompleted
    }

    fun getHideCompleted() = prefsProvider.hideCompleted

    // Add a function to mark a task as completed
    fun markTaskComplete(task: ToDoUIModel) {
        val updatedTask = task.copy(isCompleted = true)
        updateItem(updatedTask)
    }

    // Add a function to delete a task
    fun deleteTask(id: String) {
        deleteItemById(id)
    }
}
