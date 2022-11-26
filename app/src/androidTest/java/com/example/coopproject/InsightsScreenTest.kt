package com.example.coopproject

import android.util.Log
import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.onNodeWithTag
import com.example.coopproject.testUtilities.BottomAppBarOptions
import org.junit.Before
import org.junit.Test

class InsightsScreenTest() : AndroidTestBase() {

    @Before
    fun navigateToInsightsScreen(){
        navigateToInsightsScreenAndWaitToLoad()
    }

    @Test
    fun verifyInsightsScreenUI(){

    }

    private fun getWelcomeTextContentDesc(): String{
        val welcomeTextTestTag = composeTestRule.activity.getString(R.string.TestTagWelcomeText)
        return composeTestRule.onNodeWithTag(welcomeTextTestTag)
            .fetchSemanticsNode().config[SemanticsProperties.ContentDescription][0]
    }


}