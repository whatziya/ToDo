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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whatziya.todo.views.editor.items.DeadlineSelector
import com.whatziya.todo.views.editor.items.DeleteTaskButton
import com.whatziya.todo.views.editor.items.EditorTopAppBar
import com.whatziya.todo.views.editor.items.ImportanceSelector
import com.whatziya.todo.views.editor.items.TaskToDoInput

@Composable
fun EditorScreen(
    viewModel: EditorViewModel = hiltViewModel(),
    taskId: String?,
    onBack: () -> Unit
) {
    val task = viewModel.task.value
    var deadlineDate by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
    }

    Scaffold(
        topBar = {
            EditorTopAppBar(
                onBack = onBack,
                onSave = {
                    if (task?.text.isNullOrEmpty()) {
                        Toast.makeText(context, "Введите текст задачи", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.saveTask()
                        onBack()
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
                    taskText = task?.text ?: "",
                    onTextChange = { task?.text = it }
                )

                ImportanceSelector(
                    selectedImportance = viewModel.importance.intValue,
                    onImportanceChange = { viewModel.importance.intValue = it }
                )

                Spacer(Modifier.padding(vertical = 4.dp))

                DeadlineSelector(
                    onCheckedChange = { isChecked ->
                        // Handle the switch toggle
                        if (!isChecked) {
                            deadlineDate = "" // Clear date if switch is turned off
                        }
                    },
                    onDeadlineDateChange = { date ->
                        // Update the deadlineDate state when a new date is selected
                        deadlineDate = date
                    }
                )

                Spacer(Modifier.padding(vertical = 4.dp))

                DeleteTaskButton(onDelete = {
                    viewModel.deleteTask()
                    onBack()
                })
            }
        }
    )
}
