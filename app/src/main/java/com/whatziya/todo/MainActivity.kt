package com.whatziya.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.whatziya.todo.ui.theme.ToDoTheme
import com.whatziya.todo.views.editor.EditorScreen
import com.whatziya.todo.views.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home_screen",
                        Modifier.padding(innerPadding)
                    ) {
                        composable("home_screen") {
                            HomeScreen(
                                onAddNewItemClick = { navController.navigate("editor_screen") },
                                onItemClick = {taskId -> navController.navigate("editor_screen/$taskId") })
                        }
                        composable("editor_screen") { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getString("taskId")
                            EditorScreen(taskId = taskId, onBack = { navController.navigateUp() })
                        }
                        composable(
                            route = "editor_screen/${taskId}",
                            arguments = listOf(navArgument("taskId") { defaultValue = "" })
                        ) { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
                            EditorScreen(taskId = taskId, onBack = { navController.navigateUp() })
                        }
                    }
                }
            }
        }
    }
}