package com.satyasnehith.acrud.addarticle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.satyasnehith.acrud.CancellableToast
import com.satyasnehith.acrud.components.ArticleTextField
import com.satyasnehith.acud.core.network.Result
import com.satyasnehith.acud.core.network.model.Article
import com.satyasnehith.acud.core.network.model.SuccessRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

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
    addArticle: suspend (Article) -> Result<SuccessRes> =
        { Result.Success(SuccessRes("")) }
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
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = "Add Article",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            val context = LocalContext.current
            ExtendedFloatingActionButton(
                onClick = {
                    coroutineScope.launch(Dispatchers.Main) {
                        val result = addArticle(
                            Article(
                                id = null,
                                title = titleValue.value,
                                body = bodyValue.value
                            )
                        )
                        val toastMessage = when(result) {
                            is Result.Success ->  {
                                navigateBack()
                                result.data.message
                            }
                            is Result.Failure -> {
                                result.error
                            }
                        }
                        Timber.d(toastMessage)
                        Timber.tag("AddArticle").d(toastMessage)
                        CancellableToast.show(context, toastMessage)
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
        Column(modifier = Modifier.padding(paddingValues)) {
            ArticleTextField(
                placeholder = "Title",
                valueState = titleValue,
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            Divider()
            ArticleTextField(
                placeholder = "Body",
                valueState = bodyValue,
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
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
    AddArticle()
}