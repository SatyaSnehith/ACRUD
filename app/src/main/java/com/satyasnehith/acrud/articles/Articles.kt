package com.satyasnehith.acrud.articles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.satyasnehith.acrud.components.ArticleColumnTopBar
import com.satyasnehith.acrud.components.ArticleList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Articles(
    navigate: (String) -> Unit = {},
    viewModel: ArticlesViewModel = hiltViewModel(),
) {
    val articlesState = viewModel.articlesState
    LaunchedEffect(Unit) {
        viewModel.getArticles()
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArticleColumnTopBar(scrollBehavior)
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {
                    navigate("articles/add")
                },
            ) {
                Icon(Icons.Filled.Add, "Add article")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            when(articlesState.callState.value) {
                CallState.LOADING -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
                CallState.CONTENT -> {
                    ArticleList(
                        articlesState.articlesListState,
                        modifier = Modifier
                            .padding(0.dp, 4.dp)
                            .clipToBounds()
                    ) {
                        navigate("articles/view/id")
                    }
                }
                CallState.NO_CONTENT -> {
                    Text(text = articlesState.noContentText.value)
                }
            }
        }

    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun GreetingPreview() {
    Articles()
}