package ca.centennial.tg_comp304_401_lab02

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.centennial.tg_comp304_401_lab02.activities.CreateNewTaskActivity
import ca.centennial.tg_comp304_401_lab02.activities.ModifyTaskActivity
import ca.centennial.tg_comp304_401_lab02.data.Priority
import ca.centennial.tg_comp304_401_lab02.data.Task
import ca.centennial.tg_comp304_401_lab02.data.TaskRepo
import ca.centennial.tg_comp304_401_lab02.ui.theme.TG_COMP304401_Lab02Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TaskRepo.add("Task 1", "Description for Task 1", "t@t.com", Priority.High, "2024-12-31")

        //enableEdgeToEdge()
        setContent {
            TG_COMP304401_Lab02Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WritingTasksApp()

                }
            }
        }
    }
}


@Composable
fun WritingTasksApp() {
    // Initialising NavController
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onAddClick = {
                    context.startActivity(Intent(context, CreateNewTaskActivity::class.java))
                },
                onTaskClick = { id ->
                    context.startActivity(
                        Intent(
                            context,
                            ModifyTaskActivity::class.java
                        ).putExtra("taskId", id)
                    )
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onAddClick: () -> Unit, onTaskClick: (Int) -> Unit) {
    val tasks = TaskRepo.getTasks()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Centennial Task App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(tasks) { task ->
                TaskCard(task, onClick = { onTaskClick(task.id) })
            }
        }
    }
}

@Composable
fun TaskCard(task: Task, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.head, style = MaterialTheme.typography.headlineSmall)
            Text(text = task.description, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Priority: ${task.priority}", color = MaterialTheme.colorScheme.primary)
            Text(text = "Email: ${task.email}")
            Text(text = "Created: ${task.creationDate}")
            Text(text = "Due: ${task.dueDate}")
        }
    }
}

