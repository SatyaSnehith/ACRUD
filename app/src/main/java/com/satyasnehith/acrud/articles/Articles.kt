package com.satyasnehith.acrud.articles

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.satyasnehith.acrud.LogLifecycle
import com.satyasnehith.acrud.components.ArticleTopBar
import com.satyasnehith.acrud.components.ArticleList
import com.satyasnehith.acrud.data.FakeData
import com.satyasnehith.acrud.ui.theme.ACRUDTheme

@Composable
internal fun ArticlesRoute(
    navigate: (String) -> Unit = {},
    viewModel: ArticlesViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val articlesState by viewModel.articlesState.collectAsStateWithLifecycle(
        initialValue = UiState.Loading,
        lifecycle = lifecycleOwner.lifecycle,
        context = lifecycleOwner.lifecycleScope.coroutineContext
    )
    LogLifecycle(name = "Articles")
    Articles(articlesState, navigate)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Articles(
    articlesState: UiState = UiState.Success(FakeData.articles),
    navigate: (String) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArticleTopBar(scrollBehavior)
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
                .fillMaxSize(),
        ) {
            when(articlesState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(60.dp),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
                is UiState.Success -> {
                    ArticleList(
                        articlesState.items,
                    ) {
                        navigate("articles/view/${it.id}")
                    }
                }
                is UiState.Failure -> {
                    Text(
                        text = articlesState.message,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
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

@Preview(
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun GreetingPreviewDark() {
    ACRUDTheme {
        Articles()
    }
}