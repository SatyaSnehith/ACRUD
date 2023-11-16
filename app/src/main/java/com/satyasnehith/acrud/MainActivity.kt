package com.satyasnehith.acrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.satyasnehith.acrud.addarticle.AddArticleRoute
import com.satyasnehith.acrud.articles.ArticlesRoute
import com.satyasnehith.acrud.ui.theme.ACRUDTheme
import com.satyasnehith.acrud.viewartice.ViewArticleRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            ACRUDTheme {
                // A surface container using the 'background' color from the theme
                NavHost(navController = navController, startDestination = "articles") {
                    composable("articles") {
                        ArticlesRoute(
                            { navController.navigate(it) }
                        )
                    }
                    composable("articles/add") {
                        AddArticleRoute({ navController.popBackStack() })
                    }
                    composable("articles/view/{id}") {
                        ViewArticleRoute(
                            it.arguments?.getString("id")?.toIntOrNull() ?: return@composable,
                            { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
