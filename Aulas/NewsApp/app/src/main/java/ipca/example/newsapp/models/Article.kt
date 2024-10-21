package ipca.example.newsapp.models

import org.json.JSONObject
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date

fun String.encodeURL() : String{
    return  URLEncoder.encode(this, "UTF-8")
}


fun String.toDate(): Date {
    //"2024-10-20T17:30:00Z"
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    return dateFormat.parse(this)
}

fun Date.toStringDate(): String {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd")
    return dateFormat.format(this)
}

data class Article (
    var title: String?,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    var publishedAt: Date?
){
    companion object{

        fun fromJson(articleObject: JSONObject): Article {
            val title = articleObject.getString("title")
            val description = articleObject.getString("description")
            val url = articleObject.getString("url")
            val urlToImage = articleObject.getString("urlToImage")
            val publishedAt = articleObject.getString("publishedAt").toDate()
            return Article(title, description, url, urlToImage, publishedAt)
        }
    }

}