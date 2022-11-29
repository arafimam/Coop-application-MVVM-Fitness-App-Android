package com.example.coopproject

import android.util.Log
import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.coopproject.testUtilities.BottomAppBarOptions
import com.example.coopproject.utils.calculateUserPoints
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InsightsScreenTest() : AndroidTestBase() {

    @Before
    fun navigateToInsightsScreen(){
        navigateToInsightsScreenAndWaitToLoad()
    }

    @Test
    fun verifyInsightsScreenWelcomeText(){
        val dummyUserName = getDummyUserName()
        Assert.assertTrue(getWelcomeTextContentDesc().contains(dummyUserName))
    }

    @Test
    fun verifyInsightsScreenPointsCalculation(){
        val numberOfFinishedExercise = getNumberOfFinishedWorkout()
        val numberOfUnfinishedExercise = getNumberOfUnfinishedWorkout()
        val points = getPoints()
        val expectedPoint = calculateUserPoints(numberOfFinishedExercise.toInt(),numberOfUnfinishedExercise.toInt())
        Assert.assertEquals(points,expectedPoint)
    }

    private fun getWelcomeTextContentDesc(): String{
        val welcomeTextTestTag = composeTestRule.activity.getString(R.string.TestTagWelcomeText)
        return composeTestRule.onNodeWithTag(welcomeTextTestTag)
            .fetchSemanticsNode().config[SemanticsProperties.ContentDescription][0]
    }
}