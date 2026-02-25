package ca.centennial.tristang_comp304_401_test01.data

// Enum class for favourites
enum class Favourite {
    Normal, Important, Preferred
}


data class Contact(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
    val type: String,
    val favourite: Favourite
)