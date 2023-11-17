package com.satyasnehith.acrud.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.satyasnehith.acrud.data.FakeData
import com.satyasnehith.acrud.ui.theme.ACRUDTheme
import com.satyasnehith.acrud.core.model.Article

@Composable
fun ArticleList(
    articles: List<Article>,
    onItemClicked: (Article) -> Unit = {}
) {
    LazyColumn {
        itemsIndexed(articles) { index, item ->
            ArticleItem(
                item,
                modifier = Modifier
                    .padding(
                        top = if (index == 0) 6.dp else 0.dp,
                        bottom = if (index == articles.lastIndex) 6.dp else 0.dp,
                    )
            ) {
                onItemClicked(item)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleItem(
    article: Article,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 6.dp)
            .then(modifier),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()

        ) {
            Text(
                text = article.title,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = article.body,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
            )
        }
    }

}


@Preview(
    name = "Light Mode",
    showBackground = true
)
@Composable
fun ArticlesPreview() {
    ACRUDTheme {
        ArticleList(articles = FakeData.articles)
    }
}

@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun ArticlesPreviewDark() {
    ACRUDTheme {
        ArticleList(articles = FakeData.articles)
    }
}