package com.example.coopproject.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coopproject.R
import com.example.coopproject.navigation.Screens
import com.example.coopproject.screens.screenComponents.*
import com.example.coopproject.ui.theme.*
import com.example.coopproject.utils.*
import java.util.*

@Composable
fun BMIScreen(navController: NavController,sharedViewModel: SharedViewModel){

    val weight = remember {
        mutableStateOf(70f)
    }
    val weightUnit = remember {
        mutableStateOf("")
    }
    val height = remember {
        mutableStateOf(90f)
    }
    val heightUnit = remember{
        mutableStateOf("")
    }

    val unitSystem = remember {
        mutableStateOf("")
    }


    val BMI: Double = calculateBMIHelper(String.format(Locale.ENGLISH,"%f",weight.value),
    String.format(Locale.ENGLISH,"%f",height.value), weightUnit = weightUnit.value, heightUnit = heightUnit.value)
        //calculateBMIHelperFunction(weight, height, weightUnit = weightUnit.value, heightUnit = heightUnit.value)


    Scaffold(
        topBar = {
                 BMIScreenTopBar (
                     onBackClicked = {navController.navigate(Screens.INSIGHTS_SCREEN.name)}
                         )
        },

      content = {
          Surface(
              modifier = Modifier.fillMaxSize(),
              color = AppBackGroundColor
          )
          {
              Column(
                horizontalAlignment = Alignment.CenterHorizontally
              ){

                  UnitWidget(sharedViewModel = sharedViewModel, getUnitSelected = {
                      unitSystem.value = it
                      if (unitSystem.value == "Imperial Units"){
                          weightUnit.value = "Pounds"
                          heightUnit.value = "Foot"
                      }else{
                          weightUnit.value = "Kilograms"
                          heightUnit.value = "meters"
                      }
                  })
                  Divider(color = LighterAppThemeColor,
                      thickness = SMALL_THICKNESS,
                      modifier = Modifier.padding(top = PADDING_SMALL, bottom = PADDING_SMALL,
                          start = BIG_PADDING, end = BIG_PADDING) )
                  ParameterWidget(
                      parameterType = stringResource(id = R.string.weightHeading),
                      parameterUnit = if (unitSystem.value == "Metric Units"){
                          stringResource(id = R.string.kilograms)} else {
                          stringResource(id = R.string.pounds)},
                      getParameterValue = {weight.value = it},
                      parameterDefaultValue = 100f,
                      parameterMinValue = if (unitSystem.value == "Metric Units"){20f} else {45f},
                      parameterMaxValue = if (unitSystem.value == "Metric Units"){250f} else {550f}
                  )
                  Divider(color = LighterAppThemeColor,
                      thickness = SMALL_THICKNESS,
                      modifier = Modifier.padding(top = PADDING_SMALL, bottom = PADDING_SMALL,
                      start = BIG_PADDING, end = BIG_PADDING) )
                  ParameterWidget(
                      parameterType = stringResource(id = R.string.heightHeader),
                      parameterUnit = if (unitSystem.value == "Metric Units"){
                          stringResource(id = R.string.meters)} else {
                          stringResource(id = R.string.Foot)},
                      getParameterValue = {height.value = it},
                      parameterDefaultValue = 1.5f,
                      parameterMinValue = if (unitSystem.value == "Metric Units"){
                          1f} else {
                          1f},
                      parameterMaxValue =if (unitSystem.value == "Metric Units"){
                          3f} else {
                          20f}
                  )
                  Divider(color = LighterAppThemeColor,
                      thickness = SMALL_THICKNESS,
                      modifier = Modifier.padding(top = PADDING_SMALL, bottom = PADDING_MEDIUM,
                          start = BIG_PADDING, end = BIG_PADDING) )
              }

          }

      },

        bottomBar = {
            val underWeightMessage = stringResource(R.string.underWeightMsg)
            val normalWeightMessage = stringResource(R.string.healthyMsg)
            val overWeightMessage = stringResource(R.string.overWeightMsg)

            if (BMI<18.5){
                BMIScreenBottomBar(message = underWeightMessage)
            }
            else if (BMI>=18.5 && BMI<24.9){
                BMIScreenBottomBar(message = normalWeightMessage)
            }
            else{
                BMIScreenBottomBar(message = overWeightMessage)
            }


        }
    )

}

@Composable
fun BMIScreenBottomBar(
    message: String
){

    val showButton = remember {
        mutableStateOf(true)
    }
    val showMessage = remember {
        mutableStateOf(false)
    }
    if (showButton.value){
        val contentDescriptionForCheckBodyTypeButton = stringResource(R.string.cdForSubmit)
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .clearAndSetSemantics {
                    contentDescription = contentDescriptionForCheckBodyTypeButton
                }
                .padding(
                    start = BIG_PADDING,
                    end = BIG_PADDING,
                    top = PADDING_NORMAL,
                    bottom = TOP_PADDING_LARGE
                ),
            onClick = {
                      showButton.value = false
                showMessage.value = true
            },
            colors = ButtonDefaults.buttonColors(LighterAppThemeColor),
            shape = RoundedCornerShape(PADDING_NORMAL), elevation = ButtonDefaults.elevation(PADDING_NORMAL)) {
                Text(
                    text = "Check body type",
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.weight(5f)
                )

        }
    }

    if (showMessage.value){
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(125.dp),
            shape = RoundedCornerShape(topEnd = TOP_PADDING_LARGE, topStart = TOP_PADDING_LARGE),
            backgroundColor = AppThemeColor){
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                Column(verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Text(
                        text = message,
                        color = Color.White,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = SMALL_THICKNESS)
                    )
                    IconButton(onClick = {
                        showButton.value = true
                        showMessage.value = false
                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                            contentDescription = "Try again button.", tint = Color.White)

                    }

                }
            }
        }
    }


}

@Composable
fun BMIScreenTopBar(
    onBackClicked: () -> Unit,
){
    TopAppBar(
        backgroundColor = AppThemeColor,
        contentColor = Color.White
    ) {
        Box(modifier = Modifier.fillMaxWidth()){
            IconButton(onClick = { onBackClicked() }, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Button")
            }

            Text(text = stringResource(R.string.BodyTypeHeader), style = MaterialTheme.typography.h6, modifier = Modifier.align(
                Alignment.Center))
        }


    }
}