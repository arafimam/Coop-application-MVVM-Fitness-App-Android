package com.example.coopproject.screens

import android.util.Log
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coopproject.R
import com.example.coopproject.navigation.Screens
import com.example.coopproject.screens.screenComponents.AppInputNumberField
import com.example.coopproject.screens.screenComponents.AppInputTextField
import com.example.coopproject.screens.screenComponents.HeightCheckBox
import com.example.coopproject.screens.screenComponents.WeightCheckBox
import com.example.coopproject.ui.theme.*
import com.example.coopproject.utils.*
import java.util.*

@Composable
fun BMIScreen(navController: NavController,sharedViewModel: SharedViewModel){

    val weight = remember {
        mutableStateOf(70)
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


    val BMI: Double = calculateBMIHelper(String.format(Locale.ENGLISH,"%d",weight.value),
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

              ){
                  Divider(color = LighterAppThemeColor,
                      thickness = SMALL_THICKNESS,
                      modifier = Modifier.padding(top = PADDING_MEDIUM, bottom = PADDING_MEDIUM,
                          start = BIG_PADDING, end = BIG_PADDING) )
                  WeightWidget(getWeightValue = {
                      weight.value = it
                  }, sharedViewModel = sharedViewModel,
                  getWeightUnit = {
                      if (it == "পাউন্ড" || it == "কিলোগ্রাম"){
                          if (it == "পাউন্ড"){
                              weightUnit.value = "Pounds"
                          }
                          else {
                              weightUnit.value = "Kilograms"
                          }
                      }
                  }
                  )
                  Divider(color = LighterAppThemeColor,
                      thickness = SMALL_THICKNESS,
                      modifier = Modifier.padding(top = PADDING_MEDIUM, bottom = PADDING_MEDIUM,
                      start = BIG_PADDING, end = BIG_PADDING) )
                  HeightWidget(getHeightValue = {height.value = it}, sharedViewModel = sharedViewModel,
                      getHeightUnit = {
                          if (it == "ইঞ্চি" || it == "মিটার"){
                              if (it == "মিটার"){
                                  heightUnit.value = "meters"
                              }else{
                                  heightUnit.value = "Foot"
                              }
                          }
                      }
                  )
                  Divider(color = LighterAppThemeColor,
                      thickness = SMALL_THICKNESS,
                      modifier = Modifier.padding(top = PADDING_MEDIUM, bottom = PADDING_MEDIUM,
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
        Button(
            modifier = Modifier
                .fillMaxWidth()
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
            Row {
                Text(
                    text = "Checkbody type",
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.weight(5f)
                )

            }
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

@Composable
fun WeightWidget(
    sharedViewModel: SharedViewModel,
    getWeightValue : (Int) -> Unit,
    getWeightUnit : (String) -> Unit
){

    val weightUnit = remember {
        mutableStateOf("Pounds")
    }
    val weightValue = remember {
        mutableStateOf(100)
    }
    getWeightValue(weightValue.value)
    getWeightUnit(weightUnit.value)

    Card(
        backgroundColor = CardColor,
        modifier = Modifier
            .padding(
                start = BIG_PADDING, end = BIG_PADDING
            )
            .fillMaxWidth()
            .height(175.dp),
        shape = RoundedCornerShape(15.dp),

    ){
        Column(
            modifier = Modifier.padding(top = PADDING_MEDIUM),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.weightHeading), color = Color.White,
            style = MaterialTheme.typography.h4)
            WeightCheckBox(sharedViewModel = sharedViewModel, getSelectedUnit = {
                weightUnit.value = it
            })

            Row(
                modifier = Modifier.padding(top = PADDING_SMALL)
            ) {
                IconButton(onClick = {
                    weightValue.value = weightValue.value - 1
                },
                    enabled = weightValue.value > 30
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_remove_circle_24),
                        contentDescription = "Subtract value icon.",
                    tint = Color.White, modifier = Modifier.size(45.dp))

                }

                AppInputNumberField(text = String.format("%d",weightValue.value), label = "", onTextChange = {
                    if (it != ""){
                        weightValue.value = it.toInt()
                    }
                    else{
                        weightValue.value = 0
                    }
                },
                modifier = Modifier.width(80.dp),
                    textColor = Color.White
                )
                //Text(text = weightValue.value.toString(),color = Color.White, style = MaterialTheme.typography.h4,)

                IconButton(onClick = {
                    weightValue.value = weightValue.value + 1
                }) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_add_circle_24),
                        contentDescription = "Add value icon.",
                    tint = Color.White,modifier = Modifier.size(45.dp))
                }


            }
        }

    }

}

@Composable
fun HeightWidget(
    sharedViewModel: SharedViewModel,
    getHeightValue: (Float) -> Unit,
    getHeightUnit: (String) -> Unit
){

    val heightValue = remember {
        mutableStateOf(1f)
    }

    val heightUnit = remember {
        mutableStateOf("Feet")
    }
    getHeightValue(heightValue.value)
    getHeightUnit(heightUnit.value)

    Card(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(start = PADDING_NORMAL, end = PADDING_NORMAL),
        backgroundColor = CardColor,
        shape = RoundedCornerShape(15.dp)){

        Column(
            modifier = Modifier.padding(top = PADDING_MEDIUM),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = stringResource(R.string.heightHeader), color = Color.White,
                style = MaterialTheme.typography.h4)
            Row(verticalAlignment = Alignment.CenterVertically){


                Text(text = String.format("%.2f",heightValue.value.toDouble()), color = Color.White,
                    style = MaterialTheme.typography.h4,modifier = Modifier.padding(end = PADDING_MEDIUM))


                HeightCheckBox(sharedViewModel = sharedViewModel, getHeightUnit = {heightUnit.value = it})
            }
            Slider(value = heightValue.value, onValueChange = {
                heightValue.value = it
            }, valueRange = if (heightUnit.value == "meters") {0f..3f} else {2f..9f},
                colors = SliderDefaults.colors(activeTrackColor = LighterAppThemeColor, thumbColor = SliderThumbColor, disabledActiveTrackColor = Color.Blue ),
                modifier = Modifier.padding(start = PADDING_MEDIUM, end = PADDING_MEDIUM)
                )
        }

    }
}

