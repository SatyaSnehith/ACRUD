package com.satyasnehith.acrud.viewartice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyasnehith.acrud.core.data.ArticlesRepository
import com.satyasnehith.acrud.core.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewArticleViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository
): ViewModel() {
    var viewUiState: StateFlow<ViewUiState> = MutableStateFlow(ViewUiState.Loading)

    fun getArticleFlowState(id: Int) {
        viewUiState = flow {
            emit(ViewUiState.Loading)
            val result = articlesRepository.getArticle(id)
            emit(if (result.isSuccess) {
                    ViewUiState.Success(result.getOrNull()!!)
                } else {
                    ViewUiState.Failure(result.exceptionOrNull()?.message.orEmpty())
                }
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ViewUiState.Loading)
    }

    suspend fun deleteArticle(id: Int) = withContext(Dispatchers.IO) {
        Timber.tag("DELETE").d(id.toString())
        return@withContext articlesRepository.deleteArticle(id)
    }

}

sealed class ViewUiState {
    object Loading: ViewUiState()
    class Failure(val message: String): ViewUiState()
    class Success(val article: Article): ViewUiState()
}