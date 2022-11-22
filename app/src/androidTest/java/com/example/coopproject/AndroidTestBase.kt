package com.example.coopproject

import android.content.Context
import androidx.activity.viewModels
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.coopproject.data.UserDao
import com.example.coopproject.data.UserDatabase
import com.example.coopproject.screens.SharedViewModel
import com.example.coopproject.utils.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

open class AndroidTestBase {

    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Gets the number of finished workout value.
     * @return Int of the number of finished work out value.
     */
    fun getNumberOfFinishedWorkout(): Int{
        val value = composeTestRule.onNode(hasTestTag("finished"))
        return (value.fetchSemanticsNode().config[SemanticsProperties.Text][0].text).toInt()
    }

    /**
     * Gets the point wheel content description.
     */
    fun getPointWheelContentDescription(): String{
        val value = composeTestRule.onNode(hasTestTag("PointsWheel"))
       return (value.fetchSemanticsNode().config[SemanticsProperties.ContentDescription][0])
    }

    /**
     * Gets the number of unfinished workout value.
     */
    fun getNumberOfUnfinishedWorkout(): Int{
        val value = composeTestRule.onNode(hasTestTag("unfinished"))
        return (value.fetchSemanticsNode().config[SemanticsProperties.Text][0].text).toInt()
    }

    /**
     * Gets current day.
     * @return String which is current day name.
     */
    fun getCurrentDay(): String {
        val listOfDays: List<String> = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        val calendar: Calendar = Calendar.getInstance()
        return listOfDays[calendar.get(Calendar.DAY_OF_WEEK)-1]
    }

    /**
     * Gets current time in String format.
     * @return String
     */
    fun getCurrentTimeInStringFormat(): String{
        val exerciseInformation: ExerciseInformation = getDummyDataForCurrentDayExercise()
        val totalTime: Double = getTotalTimeFromListOfSubExercise(exerciseInformation.exercises)
        val hours: Int = getHours(totalTime*60)
        val minutes: Int = getMinutes(totalTime*60)
        return "$hours hours and $minutes minutes"
    }

    /**
     * Get current exercise Type
     * @return String
     */
    fun getCurrentExerciseType(): String?{
        val exerciseInformation: ExerciseInformation = getDummyDataForCurrentDayExercise()
        return exerciseInformation.exerciseType
    }

    /**
     * Navigates to insight Screen.
     */
    fun navigateToInsightsScreen(){
        val insightsScreenNavigationButton = composeTestRule.activity.getString(R.string.cdinsightsScreenNav)
        composeTestRule.onNodeWithContentDescription(insightsScreenNavigationButton).performClick()
        composeTestRule.waitForIdle()
    }

    /**
     * Navigates to Profile Screen.
     */
    fun navigateToProfileScreen(){
        val profileScreenNavigationButton = composeTestRule.activity.getString(R.string.cdProfileScreenNav)
        composeTestRule.onNodeWithContentDescription(profileScreenNavigationButton).performClick()
        composeTestRule.waitForIdle()
    }

    /**
     * Navigates to dashboard screen.
     */
    fun navigateToDashBoardScreen(){
        val dashBoardScreenButton = composeTestRule.activity.getString(R.string.cdForDashboardNav)
        composeTestRule.onNodeWithContentDescription(dashBoardScreenButton).performClick()
        composeTestRule.waitForIdle()
    }

    private fun getDummyDataForCurrentDayExercise(): ExerciseInformation {
        var sb1: SubExercise = SubExercise("Bench Press",15,2,2,30.0,false);
        var sb2: SubExercise = SubExercise("Push Up",30,3,15,20.0,false);
        var sb3: SubExercise = SubExercise("Inclined Chest Press",10,3,1,15.00,false)
        var sb4: SubExercise = SubExercise("Declined chest press",22,3,1,15.00,false)
        var sb5: SubExercise = SubExercise("Dips",11,3,1,15.00,false)
        var sb6: SubExercise = SubExercise("Dumbbell Chest Press",22,3,1,15.00,false)
        var sb7: SubExercise = SubExercise("Shoulder Bench Press",15,3,1,15.00,false)
        var listOfSubExercise = listOf<SubExercise>(sb1,sb2,sb3,sb4,sb5,sb6,sb7)

        var ex1: ExerciseInformation = ExerciseInformation("Sunday","Chest Day",listOfSubExercise)
        var ex2: ExerciseInformation = ExerciseInformation("Monday","Leg day",listOfSubExercise)
        var ex3: ExerciseInformation = ExerciseInformation("Tuesday", "Chest Day",listOfSubExercise)
        var ex4: ExerciseInformation = ExerciseInformation("Wednesday","Shoulder Day",listOfSubExercise)
        var ex5: ExerciseInformation = ExerciseInformation("Thursday","Heavy lifting",listOfSubExercise)
        var ex6: ExerciseInformation = ExerciseInformation("Friday","Easy",listOfSubExercise)
        var ex7: ExerciseInformation = ExerciseInformation("Saturday","Chest Day",listOfSubExercise)

        var listOfExercise: List<ExerciseInformation> = listOf(ex1,ex2,ex3,ex4,ex5,ex6,ex7)
        val calendar: Calendar = Calendar.getInstance()
        return listOfExercise[calendar.get(Calendar.DAY_OF_WEEK)-1]
    }
}