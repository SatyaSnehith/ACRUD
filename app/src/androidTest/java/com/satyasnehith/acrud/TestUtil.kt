package com.satyasnehith.acrud

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.satyasnehith.acrud.core.model.Article

fun SemanticsNodeInteractionsProvider.fromArticles_addArticle(article: Article) {
    onNodeWithContentDescription("Add article").performClick()
    performInputArticle(article)
    onNodeWithContentDescription("Send article").performClick()
}

fun SemanticsNodeInteractionsProvider.performInputArticle(article: Article) {
    onNodeWithTag("Title").apply {
        performTextClearance()
        performTextInput(article.title)
    }
    onNodeWithTag("Body").apply {
        performTextClearance()
        performTextInput(article.body)
    }
}

fun SemanticsNodeInteractionsProvider.clickIcon(contentDescription: String) {
    onNodeWithContentDescription(contentDescription).performClick()
}

fun SemanticsNodeInteractionsProvider.articlesLazyColumn(): SemanticsNodeInteraction {
    return onNodeWithTag("Articles")
}

fun SemanticsNodeInteractionsProvider.openArticleAndDelete(index: Int) {
    articlesLazyColumn().onChildren()[index].performClick()
    clickIcon("Delete article")
    onNodeWithText("Confirm").performClick()
}