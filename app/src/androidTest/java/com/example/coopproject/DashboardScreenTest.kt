package com.example.coopproject

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.onNodeWithContentDescription
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
import org.junit.BeforeClass


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DashBoardScreenTest() : AndroidTestBase() {

    companion object {
        @BeforeClass
        @JvmStatic
        fun enableAccessibility(){
            AccessibilityChecks.enable().setRunChecksFromRootView(true)
        }
    }

    @Test
    fun verifyDashBoardUI()
    {

        val startExerciseButton = composeTestRule.activity.getString(R.string.contDescStartExerciseButton)
        val dayName = getCurrentDay()
        val dashboardBottomNavigation = composeTestRule.activity.getString(R.string.cdForDashboardNav)
        val insightsBottomNavigation = composeTestRule.activity.getString(R.string.cdinsightsScreenNav)
        val profileBottomNavigation = composeTestRule.activity.getString(R.string.cdProfileScreenNav)
        val currentDayExerciseType = getCurrentExerciseType()
        val timerIcon = composeTestRule.activity.getString(R.string.timerIcon)
        val totalTimeForExercise = getCurrentTimeInStringFormat()

        Log.d("AndroidTest", "Verify dashboard screen shows current day.")
        composeTestRule.onNodeWithContentDescription(dayName).
        assertExists("Today's day is not visible in Dashboard UI.")

        Log.d("AndroidTest", "Verifying Start Exercise Button is visible.")
        composeTestRule.onNodeWithContentDescription(startExerciseButton).
        assertExists("Start Exercise Button is not visible in dashboard UI.")

        Log.d("AndroidTest","Verify Bottom App Bar")
        composeTestRule.onNodeWithContentDescription(dashboardBottomNavigation).
                assertExists("Dashboard Navigation not visible in Dashboard UI.")
        composeTestRule.onNodeWithContentDescription(insightsBottomNavigation).
                assertExists("Insights Navigation not visible in Dashboard UI.")
        composeTestRule.onNodeWithContentDescription(profileBottomNavigation).
                assertExists("Profile Navigation not visible in Dashboard UI.")

        Log.d("AndroidTest","Verify Exercise Type shown is correct.")
        composeTestRule.onNodeWithContentDescription(currentDayExerciseType!!).
                assertExists("Exercise type shown is not correct.")

        Log.d("AndroidTest","Verify Timer icon is visible.")
        composeTestRule.onNodeWithContentDescription(timerIcon).
                assertExists("Timer icon is not visible.")

        Log.d("Android Test","Verify total time is visible.")
        composeTestRule.onNodeWithContentDescription(totalTimeForExercise).
                assertExists("Total time for exercise is not visible in UI.")
    }

    /*
    @Test
    fun verifyNavigationToInsightsScreen()
    {
        val insightsScreenButton = composeTestRule.activity.getString(R.string.checkBodyType)
        navigateToInsightsScreen()
        composeTestRule.onNodeWithContentDescription(insightsScreenButton).
                assertExists("Insights Screen is not visible.")
    }

    @Test
    fun verifyNavigationToProfileScreen()
    {
        val ageHeadingInProfileScreen = composeTestRule.activity.getString(R.string.ageHeading)
        navigateToProfileScreen()
        composeTestRule.onNodeWithContentDescription(ageHeadingInProfileScreen).
                assertExists("Profile Screen Note visible.")
    }

    @Test
    fun verifyNavigationToDashboardScreen()
    {
        val dashboardScreenButton =
            composeTestRule.activity.getString(R.string.contDescStartExerciseButton)
        navigateToDashBoardScreen()
        composeTestRule.onNodeWithContentDescription(dashboardScreenButton)
            .assertExists("Dashboard Screen not visible.")
    }

     */
}