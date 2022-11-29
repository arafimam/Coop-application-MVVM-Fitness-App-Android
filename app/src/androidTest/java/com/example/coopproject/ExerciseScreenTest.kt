package com.example.coopproject

import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExerciseScreenTest() : AndroidTestBase() {
    @Test
    fun verifyExerciseUI(){

    }

    private fun clickOnDoneButtonPartially(){
        val testTagDoneButton = composeTestRule.activity.getString(R.string.TestTagDoneButtonExercise)
        var listOfDoneButtons = composeTestRule.onAllNodesWithTag(testTagDoneButton)
        for(i in 0..7){
            listOfDoneButtons[i].performScrollTo()
            composeTestRule.waitForIdle()
            listOfDoneButtons[i].performClick()
            composeTestRule.waitForIdle()
        }
    }


    private fun clickOnFinishExercise(){
        val tagForFinishExerciseButton = composeTestRule.activity.getString(R.string.TestTagFinishExerciseButton)
        val finishExerciseButton = composeTestRule.onNodeWithTag(tagForFinishExerciseButton)
        //TODO: To be implemented.
    }
}