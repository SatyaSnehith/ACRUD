package com.satyasnehith.acrud.articles

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.satyasnehith.acrud.components.ArticleColumnTopBar
import com.satyasnehith.acrud.components.ArticleList
import com.satyasnehith.acrud.data.FakeData
import com.satyasnehith.acud.core.network.model.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Articles(
    navController: NavController,
    articlesState: SnapshotStateList<Article>
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
        ArticleList(
            articlesState,
            modifier = Modifier
                .padding(paddingValues)
                .padding(0.dp, 4.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun GreetingPreview() {
    Articles(
        NavHostController(LocalContext.current),
        articlesState = FakeData.articles
    )
}