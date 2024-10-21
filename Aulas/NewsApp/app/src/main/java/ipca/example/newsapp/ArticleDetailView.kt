package ipca.example.newsapp

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import ipca.example.newsapp.models.Article
import java.util.Date

@Composable
fun ArticleDetailView(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)
        }
    },
        update = { webView ->
            webView.loadUrl(url ?: "")
        })
}


@Preview(showBackground = true)
@Composable
fun ArticleDetailViewPreview() {
    ArticleDetailView(url =   "http://google.com")
}

