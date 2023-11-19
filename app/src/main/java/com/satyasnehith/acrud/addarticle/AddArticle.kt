package com.satyasnehith.acrud.addarticle

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.satyasnehith.acrud.components.ArticleTextField
import com.satyasnehith.acrud.components.ArticleTopBar
import com.satyasnehith.acrud.core.database.FakeData
import com.satyasnehith.acrud.ui.theme.ACRUDTheme
import com.satyasnehith.acrud.core.model.Article
import com.satyasnehith.acrud.di.Toaster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AddArticleRoute(
    id: Int?,
    popBackStack: () -> Unit = {},
    viewModel: AddArticleViewModel = hiltViewModel()
) {


    AddArticle(
        id = id,
        navigateBack = popBackStack,
        addArticle = viewModel::addArticle,
        updateArticle = viewModel::updateArticle,
        getArticle = viewModel::getArticle,
        toaster = viewModel.toaster
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddArticle(
    id: Int? = null,
    navigateBack: () -> Unit = {},
    addArticle: suspend (Article) -> Result<Unit> = { Result.success(Unit) },
    updateArticle: suspend (Article) -> Result<Unit> = { Result.success(Unit) },
    getArticle: suspend (Int) -> Result<Article> = { Result.success(FakeData.articles[id ?: 0]) },
    toaster: Toaster = Toaster { }
) {
    val titleValue = rememberSaveable {
        mutableStateOf("")
    }
    val bodyValue = rememberSaveable {
        mutableStateOf("")
    }
    val isEditMode by rememberSaveable {
        mutableStateOf(id != null)
    }

    LaunchedEffect(Unit) {
        if (isEditMode && id != null) {
            val result = getArticle(id)
            result.onSuccess { article ->
                titleValue.value = article.title
                bodyValue.value = article.body
            }
            result.onFailure {
                toaster.showToast("Unable to get article")
            }
        }
    }

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArticleTopBar(
                title = "${if (isEditMode) "Update" else "Add"} article",
                addBackNavigation = navigateBack
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    val emptyType = when {
                        titleValue.value.trim().isEmpty() -> "title"
                        bodyValue.value.trim().isEmpty() -> "body"
                        else -> null
                    }
                    if (emptyType != null) {
                        toaster.showToast(
                            "Please enter the $emptyType"
                        )
                        return@ExtendedFloatingActionButton
                    }
                    coroutineScope.launch(Dispatchers.Main) {
                        val article = Article(
                            id = id,
                            title = titleValue.value,
                            body = bodyValue.value
                        )
                        val result =
                            if (isEditMode) updateArticle(article)
                            else addArticle(article)

                        toaster.showToast(
                            if (result.isSuccess)
                                "Article ${if (isEditMode) "updated" else "added"} successfully"
                            else
                                "Unable to ${if (isEditMode) "update" else "add"} article"
                        )
                        if (result.isSuccess) navigateBack()
                    }
                },
                icon = {
                    Icon(
                        Icons.Filled.Send,
                        if (isEditMode) "Update article" else "Send article"
                    )
                },
                text = { Text(text = if (isEditMode) "Update" else "Send") },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            ArticleTextField(
                placeholder = "Title",
                valueState = titleValue,
                textCount = 128,
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                ),
            )
            Divider()
            ArticleTextField(
                placeholder = "Body",
                valueState = bodyValue,
                textCount = 4096,
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .defaultMinSize(minHeight = 200.dp)
                    .padding(bottom = 70.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AddArticlePreview() {
    ACRUDTheme {
        AddArticle()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun AddArticlePreviewDark() {
    ACRUDTheme {
        AddArticle()
    }
}