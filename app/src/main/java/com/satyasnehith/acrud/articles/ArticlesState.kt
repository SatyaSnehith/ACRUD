package com.satyasnehith.acrud.articles

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.satyasnehith.acud.core.network.model.Article

class ArticlesState(
    var callState: MutableState<CallState> = mutableStateOf(CallState.LOADING),
    var noContentText: MutableState<String> = mutableStateOf(""),
    val articlesListState: SnapshotStateList<Article> = mutableStateListOf()
) {

    fun addAll(articles: List<Article>) {
        articlesListState.clear()
        articlesListState.addAll(articles)
    }

}

enum class CallState {
    LOADING, CONTENT, NO_CONTENT
}