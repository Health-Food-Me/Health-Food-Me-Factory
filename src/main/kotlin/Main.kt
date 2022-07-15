import com.github.doyaaaaaken.kotlincsv.dsl.context.WriteQuoteMode
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.ResponseNaverIndex
import model.Restaurant
import java.nio.charset.Charset

fun main(args: Array<String>) {
    val writer = csvWriter {
        charset = "UTF-8"
        delimiter = '\t'
        nullCode = "NULL"
        lineTerminator = "\n"
        outputLastLineTerminator = true
        quote {
            mode = WriteQuoteMode.ALL
            char = '\''
        }
    }
    val text = "sampledata.json".parse() ?: throw IllegalStateException("text null")
    val restaurants = Json.decodeFromString<ResponseNaverIndex>(text)
    val rows = restaurants.toRestaurantList().map { it.toRow() }

    writer.open("sampledata.csv") {
        writeRow(Restaurant.header())
        writeRows(rows)
    }
}

fun String.parse() = object {}.javaClass.getResource(this)?.readText(Charset.forName("utf-8"))
