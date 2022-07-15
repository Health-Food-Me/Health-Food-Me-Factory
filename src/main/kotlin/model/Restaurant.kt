package model

data class Restaurant(
    val address: String,
    val isIndoor: Boolean,
    val name: String,
    val px: Double,
    val py: Double,
    val sid: String,
    val url: String?,
)
