package kis.kan.jetreadearapp.model

data class BookItem(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)