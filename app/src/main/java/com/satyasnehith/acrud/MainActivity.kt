package com.satyasnehith.acrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                    composable(
                        "articles/add"
                        ) {
                        AddArticleRoute(
                            id = null,
                            popBackStack = { navController.popBackStack() },
                        )
                    }
                    composable(
                        "articles/edit/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) {
                        AddArticleRoute(
                            id = it.arguments?.getInt("id") ?: return@composable,
                            popBackStack = { navController.popBackStack() },
                        )
                    }
                    composable(
                        "articles/view/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                    ) {
                        ViewArticleRoute(
                            id = it.arguments?.getInt("id") ?: return@composable,
                            navigateBack = { navController.popBackStack() },
                            navigate = { route -> navController.navigate(route) }
                        )
                    }
                }
            }
        }
    }
}
