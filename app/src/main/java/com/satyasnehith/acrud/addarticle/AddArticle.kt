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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.satyasnehith.acrud.CancellableToast
import com.satyasnehith.acrud.components.ArticleTextField
import com.satyasnehith.acrud.components.ArticleTopBar
import com.satyasnehith.acrud.ui.theme.ACRUDTheme
import com.satyasnehith.acrud.core.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AddArticleRoute(
    popBackStack: () -> Unit = {},
    viewModel: AddArticleViewModel = hiltViewModel()
) {
    AddArticle(popBackStack, viewModel::addArticle)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddArticle(
    navigateBack: () -> Unit = {},
    addArticle: suspend (Article) -> Result<Unit> = { Result.success(Unit) }
) {
    val titleValue = rememberSaveable {
        mutableStateOf("")
    }
    val bodyValue = rememberSaveable {
        mutableStateOf("")
    }

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArticleTopBar(
                title = "Add Article",
                addBackNavigation = navigateBack
            )
        },
        floatingActionButton = {
            val context = LocalContext.current
            ExtendedFloatingActionButton(
                onClick = {
                    val emptyType = when {
                        titleValue.value.isEmpty() -> "title"
                        bodyValue.value.isEmpty() -> "body"
                        else -> null
                    }
                    if (emptyType != null) {
                        CancellableToast.show(
                            context,
                            "Please enter the $emptyType"
                        )
                        return@ExtendedFloatingActionButton
                    }
                    coroutineScope.launch(Dispatchers.Main) {
                        val result = addArticle(
                            Article(
                                id = -1,
                                title = titleValue.value,
                                body = bodyValue.value
                            )
                        )
                        CancellableToast.show(
                            context,
                            if (result.isSuccess) "Article added successfully"
                            else "Unable to add article"
                        )
                        if (result.isSuccess) navigateBack()
                    }
                },
                icon = {
                    Icon(
                        Icons.Filled.Send,
                        "Send article"
                    )
                },
                text = { Text(text = "Send") },
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
                textCount = 2048,
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .defaultMinSize(minHeight = 300.dp)
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