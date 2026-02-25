package ca.centennial.tg_comp304_401_lab02.activities;

import android.app.Activity
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ca.centennial.tg_comp304_401_lab02.data.Priority
import ca.centennial.tg_comp304_401_lab02.data.Task
import ca.centennial.tg_comp304_401_lab02.data.TaskRepo

class ModifyTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the task ID passed from the main screen
        val taskId = intent.getIntExtra("taskId", -1)
        if (taskId == -1) {
            finish(); return
        }

        // Load task details
        setContent {
            MaterialTheme {
                val task = remember { TaskRepo.getTaskByID(taskId) }

                if (task == null) {
                    Text("Task not found", modifier = Modifier.padding(16.dp))
                } else {
                    // Show edit form with values
                    EditTaskUI(
                        task = task,
                        onSave = { id, title, desc, email, priority, due, creation ->
                            TaskRepo.updateTask(id, title, desc, email, priority, due, creation)
                            setResult(Activity.RESULT_OK)
                            finish()
                        },
                        onCancel = { finish() }
                    )
                }
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
fun EditTaskUI(
    task: Task,
    onSave: (Int, String, String, String, Priority, String, String) -> Unit,
    onCancel: () -> Unit
) {
    // Input fields with current task info
    var title by remember { mutableStateOf(task.head) }
    var desc by remember { mutableStateOf(task.description) }
    var email by remember { mutableStateOf(task.email) }
    var due by remember { mutableStateOf(task.dueDate) }
    var priority by remember { mutableStateOf(task.priority) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(68.dp))
        Text("Edit Task", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        // Text fields for editing task info
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
        OutlinedTextField(
            value = due,
            onValueChange = { due = it },
            label = { Text("Due Date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // Dropdown for selecting priority
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

        Spacer(Modifier.height(16.dp))

        // Buttons to save or cancel, including original id and creation date
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    val t = title.trim()
                    if (t.isNotEmpty()) onSave(
                        task.id,
                        t,
                        desc.trim(),
                        email.trim(),
                        priority,
                        due.trim(),
                        task.creationDate
                    )
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
    }
}
