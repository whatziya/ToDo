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
import java.util.UUID

@HiltViewModel
class TaskEditorViewModel @Inject constructor(
    private val taskEditorUseCase: LocalEditorUseCase
) : ViewModel() {

    var importanceLevel = mutableIntStateOf(0)
    val taskData = mutableStateOf(
        ToDoUIModel(
            id = generateTaskId(),
            text = "",
            importance = importanceLevel.intValue,
            deadline = Date(),
            isCompleted = false,
            createdAt = Date(System.currentTimeMillis()),
            modifiedAt = Date(System.currentTimeMillis())
        )
    )

    fun loadTaskById(taskId: String) {
        viewModelScope.launch {
            taskEditorUseCase.getItemById(taskId).collect { taskData.value = it }
        }
    }

    fun createTask() {
        taskData.value.let {
            it.importance = importanceLevel.intValue
            viewModelScope.launch {
                taskEditorUseCase.insertItem(it)
            }
        }
    }

    fun modifyTask(task: ToDoUIModel) {
        task.importance = importanceLevel.intValue
        viewModelScope.launch {
            taskEditorUseCase.updateItem(task)
        }
    }

    fun removeTask() {
        taskData.value.let {
            viewModelScope.launch { taskEditorUseCase.deleteItem(it.id) }
        }
    }

    private fun isNewTask(task: ToDoUIModel) = task.createdAt == task.modifiedAt

    private fun generateTaskId() = UUID.randomUUID().toString()
}