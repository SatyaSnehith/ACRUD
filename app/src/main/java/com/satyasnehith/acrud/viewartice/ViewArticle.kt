package com.satyasnehith.acrud.viewartice

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.satyasnehith.acrud.CancellableToast
import com.satyasnehith.acrud.components.AlertDialog
import com.satyasnehith.acrud.components.ArticleText
import com.satyasnehith.acrud.components.ArticleTopBar
import com.satyasnehith.acrud.data.FakeData
import com.satyasnehith.acrud.ui.theme.ACRUDTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ViewArticleRoute(
    id: Int,
    navigateBack: () -> Unit = {},
    navigate: (String) -> Unit = {},
    viewModel: ViewArticleViewModel = hiltViewModel()
) {

    val viewUiState by viewModel.viewUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getArticleFlowState(id)
    }

    ViewArticle(
        viewUiState = viewUiState,
        navigateBack = navigateBack,
        onEditClicked = { navigate("articles/edit/${id}") },
        deleteArticle = { viewModel.deleteArticle(id) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewArticle(
    viewUiState: ViewUiState = ViewUiState.Success(FakeData.articles[0]),
    navigateBack: () -> Unit = {},
    onEditClicked: () -> Unit = {},
    deleteArticle: suspend () -> Result<Unit> = { Result.success(Unit) }
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val openAlertDialog = remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArticleTopBar(
                title = "Article",
                addBackNavigation = navigateBack,
                actions = {
                    IconButton(onClick = {
                        openAlertDialog.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete article"
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    onEditClicked()
                },
                icon = {
                    Icon(
                        Icons.Filled.Edit,
                        "Edit article"
                    )
                },
                text = { Text(text = "Edit") },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (viewUiState) {
                is ViewUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(60.dp),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }

                is ViewUiState.Success -> {
                    val article = viewUiState.article
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        ArticleText(
                            text = article.title,
                            textStyle = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp
                            ),
                        )
                        Divider()
                        ArticleText(
                            text = article.body,
                            textStyle = TextStyle(
                                fontSize = 18.sp
                            ),
                            modifier = Modifier
                                .padding(bottom = 70.dp)
                        )
                    }
                }

                is ViewUiState.Failure -> {
                    Text(
                        text = viewUiState.message,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
    if (openAlertDialog.value) {
        DeleteConfirmationDialog(
            onDismissRequest = {
               openAlertDialog.value = false
            },
            onConfirmation = {
                openAlertDialog.value = false
                coroutineScope.launch(Dispatchers.Main) {
                    val result = deleteArticle()
                    CancellableToast.show(context, if (result.isSuccess) "Article delete successfully" else "Unable to delete article")
                    if (result.isSuccess) navigateBack()
                }
            }
        )
    }
}

@Composable
fun DeleteConfirmationDialog(
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        onConfirmation = onConfirmation,
        dialogTitle = "Delete confirmation",
        dialogText = "Are you sure you want to delete this article?"
    )
}

@Preview
@Composable
fun ViewArticlePreview() {
    ACRUDTheme {
        ViewArticle()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ViewArticlePreviewDark() {
    ACRUDTheme {
        ViewArticle()
    }
}
