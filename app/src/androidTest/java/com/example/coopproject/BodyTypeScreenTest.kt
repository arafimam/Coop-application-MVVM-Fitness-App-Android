package com.example.coopproject

import android.util.Log
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.coopproject.testUtilities.Parameter
import com.example.coopproject.testUtilities.UnitTypeOptions
import kotlinx.coroutines.selects.select
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BodyTypeScreenTest(): AndroidTestBase() {

    @Before
    fun navigateToBMIScreen(){
        navigateToCheckBodyTypeScreenAndWaitToLoad()
    }

    @Test
    fun verifyImperialUnits(){
        selectUnits(UnitTypeOptions.Imperial_Type)
        Assert.assertEquals("Weight Unit does not match.","Pounds",
            getCurrentWeightUnit())
        Assert.assertEquals("Height Unit does not match","Feet",
            getCurrentHeightUnit())
    }

    @Test
    fun verifyMetricUnits(){
        selectUnits(UnitTypeOptions.Metric_Type)
        Assert.assertEquals("Weight Unit does not match","Kilograms",
            getCurrentWeightUnit())
        Assert.assertEquals("Height Unit does not match","Meters",
            getCurrentHeightUnit())
    }

    @Test
    fun verifyUpperBoundWeight(){
        selectUnits(UnitTypeOptions.Metric_Type)
        incrementParameterBy(Parameter.Weight,151)
        Assert.assertNotEquals("Weight is more than maximum weight","251",
            getParameterValue(Parameter.Weight))
        selectUnits(UnitTypeOptions.Imperial_Type)
        incrementParameterBy(Parameter.Weight,301)
        Assert.assertNotEquals("Weight is more than maximum value","551",
            getParameterValue(Parameter.Weight))
    }

    @Test
    fun verifyUpperBoundHeight(){
        selectUnits(UnitTypeOptions.Metric_Type)
        incrementParameterBy(Parameter.Height,2)
        Assert.assertNotEquals("Height is more than maximum height","3.5",
            getParameterValue(Parameter.Height))
        selectUnits(UnitTypeOptions.Imperial_Type)
        incrementParameterBy(Parameter.Height,19)
        Assert.assertNotEquals("Height is more than maximum value","20.5",
            getParameterValue(Parameter.Height))
    }

    @Test
    fun verifyLowerBoundWeight(){
        selectUnits(UnitTypeOptions.Imperial_Type)
        decrementParameterBy(Parameter.Weight,56)
        Assert.assertNotEquals("Weight is less than minimum","44",getParameterValue(Parameter.Weight))
        selectUnits(UnitTypeOptions.Metric_Type)
        decrementParameterBy(Parameter.Weight,26)
        Assert.assertNotEquals("Weight is lower than minimum weight","19",
            getParameterValue(Parameter.Weight))
    }

    @Test
    fun verifyLowerBoundHeight(){
        selectUnits(UnitTypeOptions.Imperial_Type)
        decrementParameterBy(Parameter.Height,2)
        Assert.assertNotEquals("Height is less than minimum","-0.5",getParameterValue(Parameter.Height))
        selectUnits(UnitTypeOptions.Metric_Type)
        decrementParameterBy(Parameter.Height,2)
        Assert.assertNotEquals("Height is less than minimum","-0.5",getParameterValue(Parameter.Height))
    }

    private fun selectUnits(unitType: UnitTypeOptions){
        val testTagForImperial = composeTestRule.activity.getString(R.string.TestTagButton1)
        val testTagForMetric = composeTestRule.activity.getString(R.string.TestTagButton2)
        if (unitType == UnitTypeOptions.Imperial_Type){
            composeTestRule.onNodeWithTag(testTagForImperial).performClick()
            composeTestRule.waitForIdle()
        }
        else if (unitType == UnitTypeOptions.Metric_Type){
            composeTestRule.onNodeWithTag(testTagForMetric).performClick()
            composeTestRule.waitForIdle()
        }else{
            Log.d("Error","No Such Unit Options.")
        }
    }

    private fun getCurrentWeightUnit() : String{
        val testTagUnit = composeTestRule.activity.getString(R.string.TestTagUnit)
        val list = composeTestRule.onAllNodesWithTag(testTag = testTagUnit).fetchSemanticsNodes()
        return list[0].config[SemanticsProperties.Text][0].text
    }

    private fun getCurrentHeightUnit(): String{
        val testTagUnit = composeTestRule.activity.getString(R.string.TestTagUnit)
        val list = composeTestRule.onAllNodesWithTag(testTag = testTagUnit).fetchSemanticsNodes()
        return list[1].config[SemanticsProperties.Text][0].text
    }

    private fun incrementParameterBy(parameterType: Parameter, incrementAmount: Int){
        val testTagForPlusButton = composeTestRule.activity.getString(R.string.TestTagPlusButton)
        val plusButtons = composeTestRule.onAllNodesWithTag(testTagForPlusButton)
        if (parameterType == Parameter.Weight){
            for (i in 0..incrementAmount){
                plusButtons[0].performClick()
                composeTestRule.waitForIdle()
            }
        }
        else if (parameterType == Parameter.Height){
            for (i in 0..incrementAmount){
                plusButtons[1].performClick()
                composeTestRule.waitForIdle()
            }
        }
    }

    private fun decrementParameterBy(parameterType: Parameter, decrementAmount: Int){
        val testTagForSubtractButton = composeTestRule.activity.getString(R.string.TestTagSubtractButton)
        val subtractButtons = composeTestRule.onAllNodesWithTag(testTagForSubtractButton)
        if (parameterType == Parameter.Weight){
            for (i in 0..decrementAmount){
                subtractButtons[0].performClick()
                composeTestRule.waitForIdle()
            }
        }
        else if (parameterType == Parameter.Height){
            for (i in 0..decrementAmount){
                subtractButtons[1].performClick()
                composeTestRule.waitForIdle()
            }
        }
    }

    private fun getParameterValue(parameterType: Parameter): String{
        val testTagForParameterValue = composeTestRule.activity.getString(R.string.TestTagForParameterValue)
        val parameterValues = composeTestRule.onAllNodesWithTag(testTagForParameterValue).fetchSemanticsNodes()
        return if (parameterType == Parameter.Weight){
            parameterValues[0].config[SemanticsProperties.Text][0].text
        } else if (parameterType == Parameter.Height){
            parameterValues[1].config[SemanticsProperties.Text][0].text
        } else {
            ""
        }
    }

    private fun clickOnCheckBodyType(){
        val testTagBodyTypeButton = composeTestRule.activity.getString(R.string.TestTagCheckBodyType)
        composeTestRule.onNodeWithTag(testTagBodyTypeButton).performClick()
    }

    private fun verifyRetryButtonVisible(){
        val testTagRetryButton = composeTestRule.activity.getString(R.string.TestTagRetryButton)
        composeTestRule.onNodeWithTag(testTagRetryButton).assertExists()
    }

}