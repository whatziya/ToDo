package com.whatziya.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.whatziya.todo.ui.theme.ToDoTheme
import com.whatziya.todo.views.editor.TaskEditorScreen
import com.whatziya.todo.views.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

const val KEY_TASK_ID = "taskId"
const val DEFAULT_TASK_ID = ""

sealed class Screen(val route: String) {
    data object Tasks : Screen("tasks")
    data object Task : Screen("task/{$KEY_TASK_ID}")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTheme {

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.Tasks.route
                ) {
                    composable(route = Screen.Tasks.route) {
                        HomeScreen(
                            onAddNewItemClick = {
                                navController.navigate(Screen.Task.route)
                            },
                            onItemClick = { taskId ->
                                navController.navigate("${Screen.Task.route}/$taskId")
                            }
                        )
                    }
                    composable(route = Screen.Task.route) {
                        TaskEditorScreen(
                            taskId = DEFAULT_TASK_ID, // Null ID for new task
                            onBack = { navController.navigateUp() }
                        )
                    }
                    composable(
                        route = "${Screen.Task.route}/{$KEY_TASK_ID}",
                        arguments = listOf(navArgument(KEY_TASK_ID) { defaultValue = DEFAULT_TASK_ID })
                    ) { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getString(KEY_TASK_ID) ?: DEFAULT_TASK_ID
                        TaskEditorScreen(
                            taskId = taskId,
                            onBack = { navController.navigateUp() }
                        )
                    }
                }
            }
        }
    }
}