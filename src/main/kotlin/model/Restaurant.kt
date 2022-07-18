package model

data class Restaurant(
    val address: String,
    val isIndoor: Boolean,
    val name: String,
    val px: Double,
    val py: Double,
    val sid: String,
    val url: String?,
) {
    fun toRow() = listOf(address, isIndoor, name, px, py, sid, url ?: "https://map.naver.com/v5/entry/place/${sid}")

    companion object {
        fun header() = listOf("주소", "isIndoor", "가게 이름", "경도", "위도", "고유 ID", "URL")
    }
}
