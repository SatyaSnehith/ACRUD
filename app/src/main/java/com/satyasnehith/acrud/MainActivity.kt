package com.satyasnehith.acrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.satyasnehith.acrud.addarticle.AddArticleRoute
import com.satyasnehith.acrud.articles.ArticlesRoute
import com.satyasnehith.acrud.ui.theme.ACRUDTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()
            ComposableLifecycle(LocalLifecycleOwner.current) { _, event ->
                Timber.tag("ComposableLifecycle Main").d(event.name)
                if (event == Lifecycle.Event.ON_RESUME) coroutineScope.launch {
//                    viewModel.getArticles()
                }
            }
            ACRUDTheme {
                // A surface container using the 'background' color from the theme
                NavHost(navController = navController, startDestination = "articles") {
                    composable("articles") {
                        ArticlesRoute(
                            { navController.navigate(it) }
                        )
                    }
                    composable("articles/add") { AddArticleRoute({ navController.popBackStack() }) }
                    composable("articles/view/id") { ViewArticle() }
                }
            }
        }
    }
}

@Composable
fun ViewArticle() {

}

@Composable
fun ComposableLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit
) {

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            onEvent(source, event)
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

