package com.satyasnehith.acrud

import androidx.lifecycle.ViewModel
import com.satyasnehith.acrud.articles.ArticlesState
import com.satyasnehith.acrud.articles.CallState
import com.satyasnehith.acud.core.network.Failure
import com.satyasnehith.acud.core.network.NetworkError
import com.satyasnehith.acud.core.network.Success
import com.satyasnehith.acud.core.network.api.ArticlesService
import com.satyasnehith.acud.core.network.executeForResult
import com.satyasnehith.acud.core.network.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor (
    private var articlesService: ArticlesService
): ViewModel() {
    val articlesState = ArticlesState()

    suspend fun getArticles() {
        articlesState.callState.value = CallState.LOADING
        val result = articlesService.getAllArticles().executeForResult()
        var hasContent = false
        when(result) {
            is Success -> {
                articlesState.addAll(result.data)
                hasContent = result.data.isNotEmpty()
                if (!hasContent) {
                    articlesState.noContentText.value = "No Content"
                }
            }
            is NetworkError -> {
                articlesState.noContentText.value = "Server unreachable"
            }
            is Failure -> {
                articlesState.noContentText.value = "Unexpected"
            }
        }
        if (hasContent) {
            articlesState.callState.value = CallState.CONTENT
        } else {
            articlesState.callState.value = CallState.NO_CONTENT
        }
        
    }

    suspend fun addArticle(article: Article) =
        articlesService.addArticle(article).executeForResult()


}

