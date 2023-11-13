package com.satyasnehith.acrud.addarticle

import androidx.lifecycle.ViewModel
import com.satyasnehith.acud.core.network.api.ArticlesService
import com.satyasnehith.acud.core.network.executeForResult
import com.satyasnehith.acud.core.network.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddArticleViewModel @Inject constructor(
    private val articlesService: ArticlesService
): ViewModel() {
    suspend fun addArticle(article: Article) =
        articlesService.addArticle(article).executeForResult()


}