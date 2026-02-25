package ca.centennial.tg_comp304_401_lab02.activities;

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.centennial.tg_comp304_401_lab02.data.Priority
import ca.centennial.tg_comp304_401_lab02.data.TaskRepo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

// Screen for creating tasks
class CreateNewTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                CreateTaskUI(
                    onSave = { title, desc, email, priority, due ->
                        TaskRepo.add(title, desc, email, priority, due)
                        setResult(RESULT_OK)
                        finish()
                    },
                    onCancel = { finish() }
                )
            }
        }
    }

    // Optional lifecycle methods
    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskUI(
    onSave: (String, String, String, Priority, String) -> Unit,
    onCancel: () -> Unit
) {
    // Input fields
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var due by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(Priority.Low) }
    var expanded by remember { mutableStateOf(false) }
    var showModal by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }



    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(68.dp))
        Text("Create Task", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        // Text fields for info
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        // Dropdown to pick priority level
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = priority.name,
                onValueChange = {},
                readOnly = true,
                label = { Text("Priority") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                Priority.entries.map { it.name }.forEach { p ->
                    DropdownMenuItem(
                        text = { Text(p) },
                        onClick = { priority = Priority.valueOf(p); expanded = false })
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        // Convert from UTC milliseconds to local date
        if (selectedDate != null) {
            val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            val calendar: Calendar = Calendar.getInstance()
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            calendar.timeInMillis = selectedDate!!
            due = formatter.format(calendar.time)
        }
        // Date picker display and button
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = if (selectedDate != null) due else "No Date Selected",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 15.dp)
            )

            IconButton(onClick = { showModal = true }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color.DarkGray,
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        //Buttons to save or cancel
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    val t = title.trim()
                    if (t.isNotEmpty()) onSave(t, desc.trim(), email.trim(), priority, due.trim())
                },
                modifier = Modifier
                    .width(156.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )
            ) { Text("Save") }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier
                    .width(156.dp)
                    .height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFF44336) // red text
                ),
                border = BorderStroke(1.dp, Color(0xFFF44336)) // red outline
            ) { Text("Cancel") }
        }

        if (showModal) {
            DatePickerModal(
                onDateSelected = {
                    selectedDate = it
                    showModal = false
                },
                onDismiss = { showModal = false }
            )
        }
    }
}

// Date picker compostable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}