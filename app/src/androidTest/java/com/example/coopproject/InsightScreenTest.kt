package com.example.coopproject

import android.util.Log
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.coopproject.utils.calculateUserPoints
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InsightScreenTest() : AndroidTestBase() {

    @Test
    fun verifyPointWheelShowsCorrectScore() {
        navigateToInsightsScreen()
        val finishedWorkout: Int = getNumberOfFinishedWorkout()
        val unfinishedWorkout: Int = getNumberOfUnfinishedWorkout()
        val expectedPoints = calculateUserPoints(finishedWorkout,unfinishedWorkout)
        val pointWheelContentDescription = getPointWheelContentDescription()
        Assert.assertEquals("Score does not match.",
            "Your Point is $expectedPoints out of 5",
            pointWheelContentDescription
        )
    }

    @Test
    fun verifyNavigationBackGoesToDashBoardScreen(){
        val backButton = composeTestRule.activity.getString(R.string.cdBackButtonInsigtsScreen)
        val startExerciseButton =  composeTestRule.activity.getString(R.string.contDescStartExerciseButton)
        Log.d("AndroidTest","Verify Dashboard screen is visible when back button is clicked,")
        navigateToInsightsScreen()
        composeTestRule.onNodeWithContentDescription(backButton).performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription(startExerciseButton).assertExists(
            "Dashboard Screen not visible."
        )
    }

    @Test
    fun verifyNavigationToGetBodyTypeScreen(){
        navigateToInsightsScreen()
        val checkBodyTypeButton = composeTestRule.activity.getString(R.string.checkBodyType)
        composeTestRule.onNodeWithContentDescription(checkBodyTypeButton).performClick()
        val submitButton = composeTestRule.activity.getString(R.string.cdForSubmit)
        composeTestRule.onNodeWithContentDescription(submitButton).assertExists(
            "Submit Button is visible."
        )
    }
}