import com.github.doyaaaaaken.kotlincsv.dsl.context.WriteQuoteMode
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.Menu
import model.ResponseNaverIndex
import model.Restaurant
import org.jsoup.Jsoup
import java.nio.charset.Charset

// https://pcmap.place.naver.com/restaurant/1486194226/menu/list
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
    val restaurantList = restaurants.toRestaurantList()
    val rows = restaurants.toRestaurantList().map { it.toRow() }

    writer.open("sampledata.csv") {
        writeRow(Restaurant.header())
        writeRows(rows)
    }
    writer.open("menu2.csv") {
        writeRow(Menu.Header())
        restaurantList.asReversed()
            .asSequence()
            .map { "https://pcmap.place.naver.com/restaurant/${it.sid}/menu/list" }
            .map {
                Jsoup.connect(it)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36")
                    .get()
            }.map {
                val restaurantName = it.select("div._1_hm2").select("span._3XamX").text()
                val menuInfo = it.select("ul._2yHts")
                    .select("li._3j-Cj")
                    .map { element ->
                        val imageUrl = runCatching {
                            element.select("div.cb7hz")
                                .attr("style")
                                .run {
                                    substring(indexOf('('), indexOf(')'))
                                }
                        }.getOrDefault("")
                        val menuName = runCatching {
                            element.select("span._3yfZ1").text()
                        }.getOrDefault("")
                        val price = runCatching {
                            element.select("div._3qFuX").text()
                        }.getOrDefault("")
                        Menu.Info(menuName, price, imageUrl)
                    }
                Menu(restaurantName, menuInfo).toRow()
            }.map {
                println(it)
                it.forEach { writeRow(it) }
            }.toList()
    }
}

fun String.parse() = object {}.javaClass.getResource(this)?.readText(Charset.forName("utf-8"))
