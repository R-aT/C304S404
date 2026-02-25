package ca.centennial.tg_comp304_401_lab02.data;

// Enum class for task priority levels
enum class Priority {
    Low, Medium, High, Critical
}

// Data class for storing task info
data class Task(
    val id: Int,
    val head: String,
    val description: String,
    val email: String,
    val priority: Priority,
    val dueDate: String,
    val creationDate: String
)