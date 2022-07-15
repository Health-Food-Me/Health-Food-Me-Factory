import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.ResponseNaverIndex
import java.nio.charset.Charset

fun main(args: Array<String>) {
    val text = "sampledata.json".parse() ?: throw IllegalStateException("text null")
    val restaurants = Json.decodeFromString<ResponseNaverIndex>(text)
    restaurants.toRestaurantList().forEach {
        println(it)
    }
}

fun String.parse() = object {}.javaClass.getResource(this)?.readText(Charset.forName("utf-8"))
