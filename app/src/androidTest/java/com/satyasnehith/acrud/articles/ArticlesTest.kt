package com.satyasnehith.acrud.articles

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.filters.LargeTest
import com.google.common.truth.Truth.assertThat
import com.satyasnehith.acrud.MainActivity
import com.satyasnehith.acrud.articles.di.FakeToaster
import com.satyasnehith.acrud.core.database.FakeData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@LargeTest
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ArticlesTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test1_CheckIfAddFabVisibility() {
        composeTestRule.onRoot().printToLog("FAB")
        composeTestRule.onNodeWithContentDescription("Add article").assertIsDisplayed()
    }

    @Test
    fun test2_toastWithAddArticleWithoutTitle() {
        composeTestRule.onNodeWithContentDescription("Add article").performClick()
        composeTestRule.onNodeWithContentDescription("Send article").performClick()
        assertThat(FakeToaster.toasts.first()).isEqualTo("Please enter the title")
    }

    @Test
    fun test3_toastWithAddArticleWithoutBody() {
        composeTestRule.onNodeWithContentDescription("Add article").performClick()
        composeTestRule.onNodeWithTag("Title").performTextInput("Sample title")
        composeTestRule.onNodeWithContentDescription("Send article").performClick()
        assertThat(FakeToaster.toasts.first()).isEqualTo("Please enter the body")
    }

    @Test
    fun test4_addArticleAndCheckInColumn() {
        val sampleArticle = FakeData.articles.random()
        val title = sampleArticle.title
        val body = sampleArticle.body

        composeTestRule.apply {
            onNodeWithContentDescription("Add article").performClick()
            onNodeWithTag("Title").performTextInput(title)
            onNodeWithTag("Body").performTextInput(body)
            onNodeWithContentDescription("Send article").performClick()
            onNodeWithTag("Articles").onChildAt(0).apply {
                assertTextContains(title)
                assertTextContains(body)
            }
        }
    }

}