package com.satyasnehith.acrud.articles

import androidx.lifecycle.ViewModel
import com.satyasnehith.acrud.core.data.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor (
    private val articlesRepository: ArticlesRepository
): ViewModel() {
    var articlesState = articlesFlow()

    private fun articlesFlow() = flow {
        val result = articlesRepository.getAllArticles()
        emit(
            if (result.isSuccess){
                val items = result.getOrNull()!!
                 if (items.isEmpty()) {
                    UiState.Failure("No Articles")
                } else {
                    UiState.Success(items)
                }
            } else {
                UiState.Failure("Articles not found")
            }
        )
    }
}

