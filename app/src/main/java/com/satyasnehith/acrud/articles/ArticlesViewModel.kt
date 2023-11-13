package com.satyasnehith.acrud.articles

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.satyasnehith.acud.core.network.Failure
import com.satyasnehith.acud.core.network.NetworkError
import com.satyasnehith.acud.core.network.Success
import com.satyasnehith.acud.core.network.api.ArticlesService
import com.satyasnehith.acud.core.network.executeForResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor (
    private var articlesService: ArticlesService
): ViewModel() {
    val articlesState = ArticlesState(
        callState = mutableStateOf(CallState.LOADING),
//        articlesListState = FakeData.articles,
//        noContentText = mutableStateOf("No Content")
    )

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



}

