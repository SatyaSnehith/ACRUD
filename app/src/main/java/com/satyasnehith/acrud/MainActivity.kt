package com.satyasnehith.acrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.satyasnehith.acrud.addarticle.AddArticle
import com.satyasnehith.acrud.articles.Articles
import com.satyasnehith.acrud.ui.theme.ACRUDTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<MainViewModel>()
        setContent {
            val navController = rememberNavController()
            LaunchedEffect(Unit) {
                viewModel.getArticles()
            }
            ACRUDTheme {
                // A surface container using the 'background' color from the theme
                NavHost(navController = navController, startDestination = "articles") {
                    composable("articles") {
                        Articles(
                            navController,
                            viewModel.articlesState
                        )
                    }
                    composable("articles/add") { AddArticle(navController, viewModel::addArticle) }
                }
            }
        }
    }
}


