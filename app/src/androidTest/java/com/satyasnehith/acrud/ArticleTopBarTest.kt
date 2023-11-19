package com.satyasnehith.acrud

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@LargeTest
@HiltAndroidTest
class ArticleTopBarTest {
    @get:Rule(order = 0)
    val hilt = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_addArticleTopBarNavigationIconClicked_goToArticles(): Unit = with(composeRule) {
        onNodeWithContentDescription("Add article").performClick()
        onNodeWithTag("ArticleTopBarTitle").printToLog("Add_Article")
        onNodeWithTag("ArticleTopBarTitle").assertTextContains("Add article")
        val backIcon = onNodeWithContentDescription("Go back")
        backIcon.assertIsDisplayed()
        backIcon.performClick()
        onNodeWithTag("ArticleTopBarTitle").assertTextContains("Articles")
    }

}