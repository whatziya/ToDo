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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatziya.todo.domain.ui_model.ToDoUIModel

@Composable
fun TaskList(
    todoItems: List<ToDoUIModel>,
    hideCompleted: Boolean,
    onItemClick: (id: String) -> Unit,
    paddingValues: PaddingValues,
    onAddNewItemClick: () -> Unit,
    onMarkComplete: (ToDoUIModel) -> Unit // Pass the callback
) {
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

            val filteredItems =
                if (hideCompleted) todoItems.filter { !it.isCompleted } else todoItems

            items(filteredItems.size) { index ->
                ToDoContainer(
                    data = filteredItems[index],
                    onClick = onItemClick,
                    onMarkComplete = onMarkComplete // Pass the callback to ToDoContainer
                )
            }

            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .clickable { onAddNewItemClick() }
                        .padding(start = 56.dp, top = 14.dp, bottom = 24.dp)
                ) {
                    Text(
                        "Новое",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        }
    }
}