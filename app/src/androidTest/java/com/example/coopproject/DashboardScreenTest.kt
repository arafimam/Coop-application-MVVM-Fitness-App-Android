package com.example.coopproject

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.test.espresso.Espresso.onView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.coopproject.model.UserInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import com.example.coopproject.testUtilities.BottomAppBarOptions
import org.junit.BeforeClass


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
    fun verifyDashBoardUI()
    {

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
}