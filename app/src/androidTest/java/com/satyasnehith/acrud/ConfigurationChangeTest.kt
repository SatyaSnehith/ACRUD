package com.satyasnehith.acrud

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.filters.LargeTest
import com.satyasnehith.acrud.addarticle.AddArticle
import com.satyasnehith.acrud.core.database.FakeData
import com.satyasnehith.acrud.viewartice.ViewArticle
import com.satyasnehith.acrud.viewartice.ViewUiState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@LargeTest
@HiltAndroidTest
class ConfigurationChangeTest {
    @get:Rule(order = 0)
    val hilt = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Test
    fun test_addArticle_addTitleAndBody_changeConfiguration_checkIfExists(): Unit = with(composeRule) {
        val id = 1
        val restorationTester = StateRestorationTester(composeRule)
        restorationTester.setContent {
            AddArticle(
                id = id
            )
        }
        val article = FakeData.articles[id]
//        onNodeWithContentDescription("Add article").performClick()
        onNodeWithTag("Title").performTextInput(article.title)
        onNodeWithTag("Body").performTextInput(article.body)
        restorationTester.emulateSavedInstanceStateRestore()
        onNodeWithTag("Title").assertTextContains(article.title)
        onNodeWithTag("Body").assertTextContains(article.body)
    }

    @Test
    fun test_addArticle_viewArticle_changeConfiguration_checkIfExists(): Unit = with(composeRule) {
        val id = 1
        val restorationTester = StateRestorationTester(composeRule)
        val article = com.satyasnehith.acrud.data.FakeData.articles[id]
        restorationTester.setContent {
            ViewArticle(
                viewUiState = ViewUiState.Success(article)
            )
        }
        restorationTester.emulateSavedInstanceStateRestore()
        onNodeWithTag("Title").assertTextContains(article.title)
        onNodeWithTag("Body").assertTextContains(article.body)
    }
}