package com.satyasnehith.acrud

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.filters.LargeTest
import com.satyasnehith.acrud.core.database.FakeData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@HiltAndroidTest
class DeleteArticlesTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test1_deleteArticle_checkInColumn(): Unit = with(composeTestRule) {
        val article = FakeData.articles.random()
        val title = article.title
        val body = article.body
        fromArticles_addArticle(article)
        val firstChild = articlesLazyColumn().onChildren().onFirst()
        firstChild.assertTextContains(title)
        firstChild.assertTextContains(body)
        firstChild.performClick()
        clickIcon("Delete article")
        onNodeWithText("Confirm").performClick()
        articlesLazyColumn().onChildren().onFirst().apply {
            onNodeWithText(title).assertDoesNotExist()
            onNodeWithText(body).assertDoesNotExist()
        }
    }

    @Test
    fun test2_addMultipleArticles_deleteArticle_checkInColumn(): Unit = with(composeTestRule) {
        val articles = FakeData.articles.subList(0, 2)
        for (article in articles) {
            fromArticles_addArticle(article)
        }
        val column = articlesLazyColumn()
        val children = column.onChildren()
        children.assertCountEquals(articles.size)
        articles.forEachIndexed { index, article ->
            val child = children[index]
            child.assertTextContains(article.title)
            child.assertTextContains(article.body)
        }
        val deleteId = articles.indices.random()
        openArticleAndDelete(deleteId)
        val deletedArticle = articles[deleteId]
        articlesLazyColumn().onChildren().onFirst().apply {
            onNodeWithText(deletedArticle.title).assertDoesNotExist()
            onNodeWithText(deletedArticle.body).assertDoesNotExist()
        }
    }
}