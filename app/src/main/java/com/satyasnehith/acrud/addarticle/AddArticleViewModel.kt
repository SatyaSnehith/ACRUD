package com.satyasnehith.acrud.addarticle

import androidx.lifecycle.ViewModel
import com.satyasnehith.acrud.core.data.ArticlesRepository
import com.satyasnehith.acrud.core.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddArticleViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository
): ViewModel() {
    suspend fun addArticle(article: Article) =
        articlesRepository.addArticle(article)


}