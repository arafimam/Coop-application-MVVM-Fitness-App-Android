package com.example.coopproject

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DashBoardScreenTest() : AndroidTestBase() {

    /**
     * Defining dashboard screen here.
     */

    @Test
    fun verifyDashBoardShowsCorrectDay()
    {
        val currentDay = getCurrentDay()
        Assert.assertEquals("Day does not match.",currentDay,getDayShownInUI())
    }

    @Test
    fun verifyExerciseDescriptionInCurrentDay(){
        val contentDescForCurrentDayExercise = getContentDescriptionOfExerciseDescription()
        val exerciseType = getCurrentExerciseType()
        val time = getCurrentTimeInStringFormat()
        Assert.assertTrue(contentDescForCurrentDayExercise.contains(time) &&
        contentDescForCurrentDayExercise.contains(exerciseType!!))
    }

    @Test
    fun verifyContentDescriptionForStartExerciseButton(){
        val contentDescForStartExerciseButton = getStartExerciseButtonContentDesc()
        Assert.assertTrue(contentDescForStartExerciseButton.contains("Start Exercise"))
    }

    @Test
    fun verifyBottomAppBar(){
        verifyHomeNavigationButton()
        verifyInsightsNavigationButton()
        verifyProfileNavigationButton()
    }

    private fun getDayShownInUI() : String{
        val dayTag = composeTestRule.activity.getString(R.string.testDagDayName)
        return composeTestRule.onNodeWithTag(dayTag)
            .fetchSemanticsNode().config[SemanticsProperties.Text][0].text
    }

    private fun getContentDescriptionOfExerciseDescription(): String{
        val exerciseTypeTag = composeTestRule.activity.getString(R.string.TestTagExerciseInfo)
        return composeTestRule.onNodeWithTag(exerciseTypeTag)
            .fetchSemanticsNode().config[SemanticsProperties.ContentDescription][0]
    }

    private fun getStartExerciseButtonContentDesc(): String {
        val startExerciseButtonTestTag = composeTestRule.activity.
        getString(R.string.TestTagStartExerciseButton)
        return composeTestRule.onNodeWithTag(startExerciseButtonTestTag).
        fetchSemanticsNode().config[SemanticsProperties.ContentDescription][0]
    }

    private fun verifyHomeNavigationButton(){
        val homeButtonTestTag = composeTestRule.activity.getString(R.string.TestTagHomeNav)
        composeTestRule.onNodeWithTag(homeButtonTestTag).assertExists("Home Navigation Button does not exist.")
    }

    private fun verifyInsightsNavigationButton(){
        val insightsTestTag = composeTestRule.activity.getString(R.string.TestTagInsightsNav)
        composeTestRule.onNodeWithTag(insightsTestTag).assertExists("Insights Navigation Button does not exist.")
    }

    private fun verifyProfileNavigationButton(){
        val profileTestTag = composeTestRule.activity.getString(R.string.TestTagNav)
        composeTestRule.onNodeWithTag(profileTestTag).assertExists("Profile Navigation Button does not exist.")
    }
}