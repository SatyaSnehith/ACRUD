package com.satyasnehith.acrud

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyasnehith.acud.core.network.api.ArticlesService
import com.satyasnehith.acud.core.network.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    var articlesService: ArticlesService
): ViewModel() {
    private val _uiState = mutableStateListOf<Article>()
    val articlesState: SnapshotStateList<Article>
        get() = _uiState

    fun getArticles() = viewModelScope.launch(Dispatchers.IO) {
        articlesService.getAllArticles().execute().body()?.let {
            _uiState.addAll(it)
        }
    }

}