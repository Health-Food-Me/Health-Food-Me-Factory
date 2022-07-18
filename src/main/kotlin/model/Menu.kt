package model

data class Menu(
    val restaurantName: String,
    val info: List<Info>
) {
    data class Info(
        val name: String,
        val price: String,
        val imageUrl: String
    )

    fun toRow() = info.map { listOf(restaurantName, it.name, it.price, it.imageUrl) }
    companion object {
        fun Header() = listOf("식당 이름", "메뉴 이름", "가격", "사진 url")
    }
}
