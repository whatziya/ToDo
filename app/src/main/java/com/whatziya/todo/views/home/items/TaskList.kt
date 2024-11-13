package com.whatziya.todo.views.home.items

import ToDoContainer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatziya.todo.domain.ui_model.ToDoUIModel

@Composable
fun TaskListView(
    tasks: List<ToDoUIModel>,
    isHideCompletedEnabled: Boolean,
    onTaskClick: (id: String) -> Unit,
    paddingValues: PaddingValues,
    onAddNewTaskClick: () -> Unit,
    onMarkTaskAsComplete: (ToDoUIModel) -> Unit
) {
    val filteredTasks = remember(isHideCompletedEnabled, tasks) {
        derivedStateOf {
            if (isHideCompletedEnabled) {
                tasks.filter { !it.isCompleted }
            } else {
                tasks
            }
        }
    }

    Surface(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
    ) {
        LazyColumn {
            item { Spacer(modifier = Modifier.padding(top = 16.dp)) }

            items(filteredTasks.value.size) { index ->
                ToDoContainer(
                    data = filteredTasks.value[index],
                    onClick = onTaskClick,
                    onMarkComplete = onMarkTaskAsComplete
                )
            }

            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .clickable { onAddNewTaskClick() }
                        .padding(start = 56.dp, top = 14.dp, bottom = 24.dp)
                ) {
                    Text(
                        "Новое дело",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        }
    }
}