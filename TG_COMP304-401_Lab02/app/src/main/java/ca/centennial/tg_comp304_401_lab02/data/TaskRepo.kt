package ca.centennial.tg_comp304_401_lab02.data


import androidx.compose.runtime.mutableStateListOf

object TaskRepo {
    private var nextId = 1

    // Kotlin collection (Additional requirements)
    private val tasks = mutableStateListOf<Task>()

    // Get all tasks
    fun getTasks(): List<Task> = tasks

    // Add a new task, filling ID and CreationDate automatically
    fun add(
        title: String,
        description: String,
        email: String,
        priority: Priority,
        dueDate: String
    ) {
        tasks.add(
            Task(
                nextId++,
                title,
                description,
                email,
                priority,
                dueDate,
                creationDate = java.time.LocalDate.now().toString()
            )
        )
    }

    // Get a task by ID
    fun getTaskByID(id: Int): Task? = tasks.find { it.id == id }


    // Update task, replacing all fields except ID and CreationDate
    fun updateTask(
        id: Int, title: String,
        description: String,
        email: String,
        priority: Priority,
        dueDate: String, creationDate: String
    ) {
        val updatedTask = Task(
            id,
            title,
            description,
            email,
            priority,
            dueDate,
            creationDate = creationDate
        )
        val index = tasks.indexOfFirst { it.id == updatedTask.id }
        if (index != -1) {
            tasks[index] = updatedTask
        }
    }


}