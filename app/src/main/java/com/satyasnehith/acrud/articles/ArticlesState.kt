package com.satyasnehith.acrud.articles

import com.satyasnehith.acud.core.network.model.Article

sealed class UiState {
    class Success(val items: List<Article>): UiState()
    class Failure(val message: String): UiState()
    object Loading: UiState()
}
