package com.whatziya.todo.views.home.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.whatziya.todo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    completedTaskCount: Int,
    isHideCompletedEnabled: Boolean,
    onToggleHideCompleted: () -> Unit
) {
    val topAppBarExpanded = topAppBarScrollBehavior.state.collapsedFraction == 0f
    val topAppBarCollapsed = topAppBarScrollBehavior.state.collapsedFraction == 1f

    Surface(shadowElevation = if (topAppBarCollapsed) 4.dp else 0.dp) {
        LargeTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                scrolledContainerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            title = {
                Row {
                    Column(Modifier.padding(start = 56.dp).weight(1f)) {
                        Text(
                            "My Tasks",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleLarge
                        )
                        if (topAppBarExpanded) {
                            Text(
                                "Completed - $completedTaskCount",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        }
                    }
                    if (topAppBarExpanded) {
                        IconButton(onClick = onToggleHideCompleted, Modifier.padding(end = 8.dp)) {
                            Icon(
                                painter = if (isHideCompletedEnabled) painterResource(R.drawable.ic_invisible) else painterResource(R.drawable.ic_visible),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            },
            actions = {
                if (topAppBarCollapsed) {
                    IconButton(onClick = onToggleHideCompleted) {
                        Icon(
                            painter = if (isHideCompletedEnabled) painterResource(R.drawable.ic_invisible) else painterResource(R.drawable.ic_visible),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            scrollBehavior = topAppBarScrollBehavior
        )
    }
}