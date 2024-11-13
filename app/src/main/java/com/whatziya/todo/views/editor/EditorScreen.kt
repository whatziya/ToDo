package com.whatziya.todo.views.editor

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whatziya.todo.DEFAULT_TASK_ID
import com.whatziya.todo.views.editor.items.DeadlineSelector
import com.whatziya.todo.views.editor.items.DeleteTaskButton
import com.whatziya.todo.views.editor.items.EditorTopAppBar
import com.whatziya.todo.views.editor.items.ImportanceSelector
import com.whatziya.todo.views.editor.items.TaskToDoInput
import java.util.Date

sealed class TaskScreenMode {
    data object New : TaskScreenMode()
    data object Edit : TaskScreenMode()
}

@Composable
fun TaskEditorScreen(
    viewModel: TaskEditorViewModel = hiltViewModel(),
    taskId: String,
    onBack: () -> Unit
) {

    val screenMode = if (taskId == DEFAULT_TASK_ID) TaskScreenMode.New else TaskScreenMode.Edit
    if(screenMode is TaskScreenMode.Edit){
        viewModel.loadTaskById(taskId)
    }
    val task = viewModel.taskData.value

    val context = LocalContext.current
    var deadline by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            EditorTopAppBar(
                onNavigateBack = onBack,
                onSaveTask = {
                    if (viewModel.taskData.value.text.isEmpty()) {
                        Toast.makeText(context, "Введите текст задачи", Toast.LENGTH_SHORT).show()
                    } else {
                        when (screenMode) {
                            is TaskScreenMode.New -> {
                                viewModel.createTask()
                                onBack()
                            }
                            is TaskScreenMode.Edit -> {
                                task.modifiedAt = Date()
                                viewModel.modifyTask(task)
                                onBack()
                            }
                        }
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                TaskToDoInput(
                    taskText = task.text,
                    onTextChange = { task.text = it }
                )

                ImportanceSelector(
                    selectedImportance = viewModel.importanceLevel.intValue,
                    onImportanceChange = { viewModel.importanceLevel.intValue = it }
                )

                Spacer(Modifier.padding(vertical = 4.dp))

                DeadlineSelector(
                    onCheckedChange = { isChecked ->
                        if (!isChecked) {
                            deadline = ""
                        }
                    },
                    onDeadlineDateChange = { date ->
                        deadline = date
                    }
                )

                Spacer(Modifier.padding(vertical = 4.dp))

                DeleteTaskButton(onDeleteTask = {
                    viewModel.removeTask()
                    onBack()
                })
            }
        }
    )
}
