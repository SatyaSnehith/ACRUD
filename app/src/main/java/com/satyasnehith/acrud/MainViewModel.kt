package com.satyasnehith.acrud

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.satyasnehith.acud.core.network.api.ArticlesService
import com.satyasnehith.acud.core.network.executeForResult
import com.satyasnehith.acud.core.network.executeForSuccessData
import com.satyasnehith.acud.core.network.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private var articlesService: ArticlesService
): ViewModel() {
    private val _uiState = mutableStateListOf<Article>()
    val articlesState: SnapshotStateList<Article>
        get() = _uiState

    suspend fun getArticles() = articlesService.getAllArticles().executeForSuccessData()?.let {
        _uiState.clear()
        _uiState.addAll(it)
    }

    suspend fun addArticle(article: Article) =
        articlesService.addArticle(article).executeForResult()


}