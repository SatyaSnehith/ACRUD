package com.satyasnehith.acrud.addarticle

import androidx.lifecycle.ViewModel
import com.satyasnehith.acrud.core.data.ArticlesRepository
import com.satyasnehith.acrud.core.model.Article
import com.satyasnehith.acrud.di.Toaster
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddArticleViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    val toaster: Toaster
): ViewModel() {
    suspend fun addArticle(article: Article) =
        articlesRepository.addArticle(article)

    suspend fun updateArticle(article: Article) =
        articlesRepository.updateArticle(article)

    suspend fun getArticle(id: Int) =
        articlesRepository.getArticle(id)

}