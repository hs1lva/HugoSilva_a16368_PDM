package ipca.example.newsapp

import androidx.lifecycle.ViewModel
import ipca.example.newsapp.models.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

data class ArticleState (
    val articles : ArrayList<Article> = arrayListOf<Article>(),
    val isLoading  : Boolean = false,
    val errorMessage: String = "",
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ArticleState())
    val uiState: StateFlow<ArticleState> = _uiState.asStateFlow()


    fun fetchArticles() {
        _uiState.value = ArticleState(
            isLoading = true
        )
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://newsapi.org/v2/top-headlines?country=us&category=sports&apiKey=852939bf21b3425d86947ec39ddd330e")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                _uiState.value = ArticleState(
                    errorMessage = e.message ?: "",
                    isLoading = false
                )
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    var articlesResult = arrayListOf<Article>()
                    val result = response.body!!.string()

                    val jsonObject = JSONObject(result)
                    val status = jsonObject.getString("status")
                    if (status == "ok") {
                        val articlesArray = jsonObject.getJSONArray("articles")
                        for (index in 0 until articlesArray.length()) {
                            val articleObject = articlesArray.getJSONObject(index)
                            val article = Article.fromJson(articleObject)
                            articlesResult.add(article)
                            println(article.urlToImage)
                        }
                        _uiState.value = ArticleState(
                            articles = articlesResult,
                            isLoading = false
                        )
                    }
                }
            }
        })
    }
}