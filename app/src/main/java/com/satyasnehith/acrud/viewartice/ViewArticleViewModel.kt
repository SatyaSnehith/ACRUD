package com.satyasnehith.acrud.viewartice

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyasnehith.acud.core.network.Result
import com.satyasnehith.acud.core.network.api.ArticlesService
import com.satyasnehith.acud.core.network.executeForResult
import com.satyasnehith.acud.core.network.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewArticleViewModel @Inject constructor(
    private val articlesService: ArticlesService
): ViewModel() {
    var viewUiState: StateFlow<ViewUiState> = MutableStateFlow(ViewUiState.Loading)

    fun getArticleFlowState(id: Int) {
        viewUiState = flow {
            emit(ViewUiState.Loading)
            val result = articlesService.getArticle(id).executeForResult()
            emit(when(result) {
                is Result.Success -> ViewUiState.Success(result.data)
                is Result.Failure -> ViewUiState.Failure(result.error)
            })
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ViewUiState.Loading)
    }

    suspend fun deleteArticle(id: Int) = withContext(Dispatchers.IO) {
        return@withContext articlesService.deleteArticle(id).executeForResult()
    }

}

sealed class ViewUiState {
    object Loading: ViewUiState()
    class Failure(val message: String): ViewUiState()
    class Success(val article: Article): ViewUiState()
}