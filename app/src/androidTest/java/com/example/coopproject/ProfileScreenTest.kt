package com.example.coopproject

import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileScreenTest: AndroidTestBase() {

    @Test
    fun verifyNoDoneButtonWithoutChangingInformation(){
        val doneButton = composeTestRule.activity.getString(R.string.cdDoneButton)
        navigateToProfileScreen()
        composeTestRule.onNodeWithContentDescription(doneButton).assertDoesNotExist()
    }

    @Test
    fun verifyDoneButtonWhenUserNameChanged(){
        navigateToProfileScreen()
        val userNameTextField = composeTestRule.onNodeWithTag("userName")
        userNameTextField.performTextReplacement("Dummy user")
        //userNameTextField.performTextInput("Dummy User")
    }
}