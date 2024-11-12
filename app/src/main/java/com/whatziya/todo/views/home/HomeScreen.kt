package com.whatziya.todo.views.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.whatziya.todo.views.home.items.TasksTopAppBar
import com.whatziya.todo.views.home.items.AddTaskFab
import com.whatziya.todo.views.home.items.TaskList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onAddNewItemClick: () -> Unit,
    onItemClick: (String) -> Unit
) {
    val topAppBarScrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var hideCompleted by rememberSaveable { mutableStateOf(viewModel.getHideCompleted()) }
    val todoItems by viewModel.toDoItems.collectAsState()
    Scaffold(
        topBar = {
            TasksTopAppBar(
                topAppBarScrollBehavior,
                todoItems.size,
                hideCompleted,
                onToggleShowCompleted = {
                    hideCompleted = !hideCompleted
                    viewModel.setHideCompleted(hideCompleted)
                })
        },
        floatingActionButton = {
            AddTaskFab(onAddNewItemClick)
        }
    ) { paddingValues ->
        TaskList(
            todoItems = todoItems,
            hideCompleted = viewModel.getHideCompleted(),
            onItemClick = onItemClick,
            paddingValues = paddingValues,
            onAddNewItemClick = onAddNewItemClick,
            onMarkComplete = { task -> viewModel.markTaskComplete(task) } // Pass the callback
        )
    }
}