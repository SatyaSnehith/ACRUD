package com.satyasnehith.acrud.articles

import android.util.Log
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.test.filters.LargeTest
import com.satyasnehith.acrud.MainActivity
import com.satyasnehith.acrud.core.database.FakeData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@LargeTest
@HiltAndroidTest
class AddArticlesTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addAllFakeArticleAndCheckInColumn() {
        val articles = FakeData.articles
        articles.forEachIndexed { index, article ->
//            val article = FakeData.articles.random()
            val title = article.title
            val body = article.body
            Log.d("TAG_addAllFakeArticleAndCheckInColumn", "$index: $title")

            composeTestRule.apply {
                onNodeWithContentDescription("Add article").performClick()
                onNodeWithTag("Title").performTextInput(title)
                onNodeWithTag("Body").performTextInput(body)
                onNodeWithContentDescription("Send article").performClick()
            }
        }
        Thread.sleep(1000)
        composeTestRule.apply {
            val articlesColumn = onNodeWithTag("Articles")
            articlesColumn.performScrollToIndex(articles.lastIndex)
            articlesColumn.onChildren().assertCountEquals(articles.size)
            articles.forEachIndexed { index, article ->
                val title = article.title
                val body = article.body
                articlesColumn.performScrollToIndex(index)
                onNodeWithText(title).assertIsDisplayed()
                onNodeWithText(body).assertIsDisplayed()
            }
        }
    }
}