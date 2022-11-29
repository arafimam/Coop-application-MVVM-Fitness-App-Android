package com.example.coopproject

import android.util.Log
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileScreenTest(): AndroidTestBase() {

    @Before
    fun navigateToProfileScreen(){
        navigateToProfileScreenAndWaitToLoad()
    }

    @Test
    fun verifyProfileInformationIsPreFilled(){
        Assert.assertTrue(
            getUserName().isNotEmpty() && getAge().isNotEmpty() && getSelectedBodyType().isNotEmpty()
        )
    }

    @Test
    fun verifyDoneButtonNotSeenInitially(){
        verifyDoneNotVisible()
    }

    @Test
    fun verifyProfileBottomBarMessage(){
        val message = getProfileScreenBottomBarMessage()
        val expectedMessage =
            composeTestRule.activity.getString(R.string.warningText1Profile)+
                    " " + composeTestRule.activity.getString(R.string.warningText2Profile)
        Assert.assertEquals("Message Does not match.",
            expectedMessage,message
        )
    }

    private fun getUserName(): String{
        val userNameTag = composeTestRule.activity.getString(R.string.TestTagUserNameTextField)
        return composeTestRule.onNodeWithTag(userNameTag).fetchSemanticsNode().config[SemanticsProperties.Text][0].text
    }

    private fun getAge(): String{
        val ageTag = composeTestRule.activity.getString(R.string.TestTagAge)
        return composeTestRule.onNodeWithTag(ageTag).fetchSemanticsNode().config[SemanticsProperties.Text][0].text
    }

    private fun getSelectedBodyType() : String{
        val hoursTag = composeTestRule.activity.getString(R.string.TestTagRadioButton)
        return composeTestRule.onAllNodesWithTag(hoursTag).fetchSemanticsNodes()[0].config[SemanticsProperties.Text][0].text
    }

    private fun getProfileScreenBottomBarMessage() : String{
        val bottomBarTag = composeTestRule.activity.getString(R.string.TestTagProfileScreenBottomBar)
        return composeTestRule.onNodeWithTag(bottomBarTag).fetchSemanticsNode().config[SemanticsProperties.ContentDescription][0]
    }

    private fun verifyDoneNotVisible(){
        val doneButtonTestTag = composeTestRule.activity.getString(R.string.TestTagDoneButton)
        composeTestRule.onNodeWithTag(doneButtonTestTag).assertDoesNotExist()
    }

}