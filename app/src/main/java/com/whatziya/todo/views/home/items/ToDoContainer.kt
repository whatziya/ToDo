import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.whatziya.todo.R
import com.whatziya.todo.domain.ui_model.ToDoUIModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ToDoContainer(
    data: ToDoUIModel,
    onClick: (taskId: String) -> Unit,
    onMarkComplete: (ToDoUIModel) -> Unit // New callback to mark the task as completed
){
    Row(
        modifier = Modifier
            .clickable { onClick(data.id) }
            .fillMaxWidth()
            .height(72.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = data.isCompleted,
            onCheckedChange = { isChecked ->
                // Update the task completion status in the ViewModel
                onMarkComplete(data.copy(isCompleted = isChecked))
            },
            enabled = !data.isCompleted, // Disable checkbox if task is already completed
            colors = if (data.isCompleted) {
                CheckboxColors(
                    checkedCheckmarkColor = Color.White,
                    uncheckedCheckmarkColor = Color.Transparent,
                    checkedBoxColor = MaterialTheme.colorScheme.secondary, // yashil rang
                    uncheckedBoxColor = Color.Transparent,
                    disabledCheckedBoxColor = MaterialTheme.colorScheme.secondary, // yashil rang
                    disabledUncheckedBoxColor = Color.Transparent,
                    disabledIndeterminateBoxColor = Color.Transparent,
                    checkedBorderColor = MaterialTheme.colorScheme.secondary, // yashil rang
                    uncheckedBorderColor = Color.Transparent,
                    disabledBorderColor = MaterialTheme.colorScheme.secondary, // yashil rang
                    disabledUncheckedBorderColor = Color.Transparent,
                    disabledIndeterminateBorderColor = Color.Transparent
                )
            } else if (data.importance == 0 || data.importance == 1) {
                CheckboxColors(
                    checkedCheckmarkColor = Color.Transparent,
                    uncheckedCheckmarkColor = Color.Transparent,
                    checkedBoxColor = Color.Transparent,
                    uncheckedBoxColor = Color.Transparent,
                    disabledCheckedBoxColor = Color.Transparent,
                    disabledUncheckedBoxColor = Color.Transparent,
                    disabledIndeterminateBoxColor = Color.Transparent,
                    checkedBorderColor = Color.Transparent,
                    uncheckedBorderColor = MaterialTheme.colorScheme.onBackground, // kulrang
                    disabledBorderColor = Color.Transparent,
                    disabledUncheckedBorderColor = Color.Transparent,
                    disabledIndeterminateBorderColor = Color.Transparent
                )
            } else {
                CheckboxColors(
                    checkedCheckmarkColor = Color.Transparent,
                    uncheckedCheckmarkColor = Color.Transparent,
                    checkedBoxColor = Color.Transparent,
                    uncheckedBoxColor = Color(0xFFFF7B70), // qizil rang
                    disabledCheckedBoxColor = Color.Transparent,
                    disabledUncheckedBoxColor = Color.Transparent,
                    disabledIndeterminateBoxColor = Color.Transparent,
                    checkedBorderColor = Color.Transparent,
                    uncheckedBorderColor = MaterialTheme.colorScheme.error, // qizil rang
                    disabledBorderColor = Color.Transparent,
                    disabledUncheckedBorderColor = Color.Transparent,
                    disabledIndeterminateBorderColor = Color.Transparent
                )
            }
        )

        Row(
            Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
                .weight(1f)
            , verticalAlignment = Alignment.CenterVertically
        ) {
            if (data.importance != 0) {
                Image(
                    modifier = Modifier.padding(end = 8.dp),
                    contentDescription = null,
                    painter = if (data.importance == 1) painterResource(R.drawable.ic_low) else painterResource(
                        R.drawable.ic_important
                    )
                )
            }
            Column{
                Text(
                    text = data.text,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = if (data.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (data.isCompleted) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary
                )
                data.deadline?.let { deadline ->
                    Text(
                        text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(deadline),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = "Info icon",
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp)
        )
    }
}