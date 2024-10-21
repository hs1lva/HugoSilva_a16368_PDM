package ipca.example.newsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.android.volley.toolbox.ImageRequest
import ipca.example.newsapp.models.Article
import ipca.example.newsapp.models.toStringDate
import java.util.Date

@Composable
fun ArticleRowView(modifier: Modifier = Modifier, article: Article) {
    Row (modifier = modifier){
        article.urlToImage?.let {
            AsyncImage(
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp),
                model = it,
                contentDescription = "Article Image"
            )
        }?:run {
            Image(
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp),
                painter = painterResource(id = R.drawable.baseline_photo_camera_back_24),
                contentDescription = "Article Image"
            )
        }
        Column (modifier = Modifier.fillMaxWidth()){
            Text(text = article.title ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = article.description ?: "",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,)

            Text(modifier = Modifier.padding(top = 8.dp), text = article.publishedAt?.toStringDate() ?: "")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleRowViewPreview() {
    val article = Article(title = "title", description = "description", url = "url", urlToImage = null, publishedAt = Date())
    ArticleRowView(article = article)
}
