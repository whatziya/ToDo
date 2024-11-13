package com.whatziya.todo.views.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whatziya.todo.domain.ui_model.ToDoUIModel
import com.whatziya.todo.domain.usecase.todo.LocalHomeUseCase
import com.whatziya.todo.preferences.PreferencesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferencesProvider: PreferencesProvider,
    private val homeUseCase: LocalHomeUseCase
) : ViewModel() {

    private val _todoItems = MutableLiveData<List<ToDoUIModel>>()
    val todoItems: LiveData<List<ToDoUIModel>> get() = _todoItems

    private val _isHideCompletedEnabled = mutableStateOf(false)
    val isHideCompletedEnabled: State<Boolean> = _isHideCompletedEnabled

    init {
        viewModelScope.launch {
            homeUseCase.getAllItems()
                .collect { items ->
                    if (_isHideCompletedEnabled.value) {
                        _todoItems.value = items.filter { !it.isCompleted }
                    } else {
                        _todoItems.value = items
                    }
                }
        }
    }

    fun updateTodoItem(item: ToDoUIModel) = viewModelScope.launch {
        homeUseCase.updateItem(item)
    }

    fun deleteTodoItemById(id: String) = viewModelScope.launch {
        homeUseCase.deleteItemById(id)
    }

    fun toggleHideCompleted() {
        viewModelScope.launch {
            _isHideCompletedEnabled.value = !_isHideCompletedEnabled.value.also {
                preferencesProvider.hideCompleted = it
            }
        }
    }

    fun getHideCompletedPreference() = preferencesProvider.hideCompleted

    fun markTodoItemAsCompleted(task: ToDoUIModel) {
        val updatedTask = task.copy(isCompleted = true)
        updateTodoItem(updatedTask)
    }
}