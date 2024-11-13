package com.whatziya.todo.views.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.whatziya.todo.views.home.items.AddTaskButton
import com.whatziya.todo.views.home.items.HomeTopAppBar
import com.whatziya.todo.views.home.items.TaskListView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onAddNewItemClick: () -> Unit,
    onItemClick: (String) -> Unit
) {
    val topAppBarScrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val hideCompletedEnabled = viewModel.isHideCompletedEnabled
    val tasks by viewModel.todoItems.observeAsState(emptyList())

    LaunchedEffect(hideCompletedEnabled.value) { }

    Scaffold(
        topBar = {
            HomeTopAppBar(
                topAppBarScrollBehavior,
                tasks.filter { it.isCompleted }.size,
                hideCompletedEnabled.value,
                onToggleHideCompleted = {
                    viewModel.toggleHideCompleted()
                }
            )
        },
        floatingActionButton = {
            AddTaskButton(onAddNewItemClick)
        }
    ) { paddingValues ->
        TaskListView(
            tasks = tasks,
            isHideCompletedEnabled = hideCompletedEnabled.value,
            onTaskClick = onItemClick,
            paddingValues = paddingValues,
            onAddNewTaskClick = onAddNewItemClick,
            onMarkTaskAsComplete = { task -> viewModel.markTodoItemAsCompleted(task) }
        )
    }
}