package ipca.example.newsapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.example.newsapp.models.encodeURL
import ipca.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun HomeView( modifier: Modifier = Modifier ,
              onArticleClick: (String) -> Unit = {}) {

    val viewModel : HomeViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        Box(modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center)
        {
            Text(text = "Loading...")
        }
    }else if(uiState.errorMessage.isNotEmpty()){
        Box(modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center)
        {
            Text(text = uiState.errorMessage)
        }
    }else{
        LazyColumn(modifier = modifier.fillMaxSize()) {
            itemsIndexed(
                items = uiState.articles
            ){
                    _, item ->
                ArticleRowView(modifier = modifier
                    .clickable {
                        onArticleClick(item.url?.encodeURL() ?: "")
                    },
                    article = item)
            }
        }
    }


    LaunchedEffect(key1 = true) {
        viewModel.fetchArticles()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    NewsAppTheme {
        HomeView()
    }
}