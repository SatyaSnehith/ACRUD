package com.satyasnehith.acrud.articles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.satyasnehith.acrud.components.ArticleColumnTopBar
import com.satyasnehith.acrud.components.ArticleList
import com.satyasnehith.acrud.data.FakeData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Articles(
    navController: NavController = NavHostController(LocalContext.current),
    articlesState: ArticlesState = ArticlesState(
        callState = mutableStateOf(CallState.LOADING),
        articlesListState = FakeData.articles,
        noContentText = mutableStateOf("No Content")
    )
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArticleColumnTopBar(scrollBehavior)
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {
                    navController.navigate("articles/add")
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
                    )
                }
                CallState.NO_CONTENT -> {
                    Text(text = articlesState.noContentText.value)
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun GreetingPreview() {
    Articles()
}