package com.satyasnehith.acrud

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.filters.LargeTest
import com.satyasnehith.acrud.core.database.FakeData
import com.satyasnehith.acrud.core.model.Article
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@LargeTest
@HiltAndroidTest
class UpdateArticlesTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_updateArticle_checkInColumn(): Unit = with(composeTestRule) {
        val subListRange = 0..3
        val articles = FakeData.articles.subList(subListRange.first, subListRange.last)
        articles.forEach { article: Article ->
            fromArticles_addArticle(article)
        }
        val randomArticleIndex = subListRange.random()
        articlesLazyColumn().onChildren()[randomArticleIndex].performClick()
        clickIcon("Edit article")
        val updatedArticle = FakeData.articles[subListRange.last]
        performInputArticle(updatedArticle)
        clickIcon("Update article")
        clickIcon("Go back")
        articlesLazyColumn().onChildren()[randomArticleIndex].apply {
            onNodeWithText(updatedArticle.title).assertIsDisplayed()
            onNodeWithText(updatedArticle.body).assertIsDisplayed()
        }
    }
}