package com.whatziya.todo.views.editor

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whatziya.todo.domain.ui_model.ToDoUIModel
import com.whatziya.todo.domain.usecase.todo.LocalEditorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val editorUseCase: LocalEditorUseCase
) : ViewModel() {

    val task = mutableStateOf<ToDoUIModel?>(null)
    var importance = mutableIntStateOf(0) // Default importance level

    // Load task if editing, otherwise create a new task
    fun loadTask(taskId: String?) {
        if (taskId != null) {
            viewModelScope.launch {
                editorUseCase.getItemById(taskId).collect { task.value = it }
            }
        } else {
            // Initialize new task
            task.value = ToDoUIModel(
                id = generateUniqueId(), // Placeholder for unique ID generation
                text = "",
                importance = importance.intValue,
                deadline = null,
                isCompleted = false,
                createdAt = Date(),
                modifiedAt = Date()
            )
        }
    }

    fun saveTask() {
        task.value?.let {
            it.importance = importance.intValue
            viewModelScope.launch {
                if (isNewTask(it)) {
                    editorUseCase.insertItem(it)
                } else {
                    editorUseCase.updateItem(it)
                }
            }
        }
    }

    fun deleteTask() {
        task.value?.let {
            viewModelScope.launch { editorUseCase.deleteItem(it) }
        }
    }

    private fun isNewTask(task: ToDoUIModel) = task.createdAt == task.modifiedAt

    // Generate unique ID for new tasks (can be UUID)
    private fun generateUniqueId() = java.util.UUID.randomUUID().toString()
}
