package ca.qc.cegepheritage.g30a2

data class Order(
    val id: Int,
    val customerName: String,
    val drink: Drink? = null,
    val food: Food? = null
)