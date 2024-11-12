package com.whatziya.todo.views.editor.items

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun DeadlineSelector(
    onCheckedChange: (Boolean) -> Unit,
    onDeadlineDateChange: (String) -> Unit  // Callback to set the deadline date
) {
    var isSwitchChecked by remember { mutableStateOf(false) }
    var selectedDateText by remember { mutableStateOf("") }
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .padding(8.dp)
            .height(72.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                "Сделать до",
                modifier = Modifier.padding(vertical = 1.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = selectedDateText.ifEmpty { "Дата не выбрана" },
                modifier = Modifier.padding(vertical = 1.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isSwitchChecked,
            onCheckedChange = { isChecked ->
                isSwitchChecked = isChecked
                onCheckedChange(isChecked)

                if (isChecked) {
                    showDatePickerDialog(context) { date ->
                        selectedDateText = date
                        onDeadlineDateChange(date)
                    }
                } else {
                    selectedDateText = ""
                    onDeadlineDateChange("")  // Clear deadline date
                }
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = MaterialTheme.colorScheme.secondary
            )
        )
    }
}

fun showDatePickerDialog(
    context: Context,
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay.${selectedMonth + 1}.$selectedYear"
            onDateSelected(selectedDate)
        },
        year,
        month,
        day
    )

    // Set minimum date to prevent past dates selection
    datePickerDialog.datePicker.minDate = calendar.timeInMillis
    datePickerDialog.show()
}
