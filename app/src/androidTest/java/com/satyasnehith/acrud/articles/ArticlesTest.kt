package com.satyasnehith.acrud.articles

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.satyasnehith.acrud.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticlesTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun checkIfAddFabVisibility() {
        composeTestRule.onRoot().printToLog("FAB")
        composeTestRule.onNodeWithContentDescription("Add article").assertIsDisplayed()
        Thread.sleep(1000)
    }

    @Test
    fun scrollTheArticles() {
//        composeTestRule.onNodeWithText("What is Lorem Ipsum").performScrollTo()
    }
}