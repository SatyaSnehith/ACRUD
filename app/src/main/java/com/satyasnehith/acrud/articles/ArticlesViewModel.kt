package com.satyasnehith.acrud.articles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyasnehith.acud.core.network.Result
import com.satyasnehith.acud.core.network.api.ArticlesService
import com.satyasnehith.acud.core.network.executeForResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor (
    private var articlesService: ArticlesService
): ViewModel() {
    var articlesState = articlesFlow()

    private fun articlesFlow() = flow {
        emit(UiState.Loading)
        val result = articlesService.getAllArticles().executeForResult()
        emit(when(result) {
            is Result.Success -> {
                val items = result.data
                 if (items.isEmpty()) {
                    UiState.Failure("No Content")
                } else {
                    UiState.Success(items)
                }
            }
            is Result.NetworkError -> {
                UiState.Failure("Server unreachable")
            }
            is Result.Failure -> {
                UiState.Failure("Unexpected")
            }
        })
    }
}

