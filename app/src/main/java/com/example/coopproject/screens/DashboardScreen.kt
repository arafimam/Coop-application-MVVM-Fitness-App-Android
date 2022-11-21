package com.example.coopproject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.coopproject.R
import com.example.coopproject.navigation.Screens
import com.example.coopproject.ui.theme.*
import com.example.coopproject.utils.*
import java.util.*

@Composable
fun DashboardScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    signedUpUser: String = sharedViewModel.signedUpUser
){

    // Fetch user information to be used throughout the dashboard screen.
    LaunchedEffect(key1 = true){
        sharedViewModel.getUserExerciseInformation(signedUpUser)
    }
    val userExerciseInformation by sharedViewModel.userExerciseInfoData.collectAsState()
    val currentDayExerciseInfo: ExerciseInformation? = sharedViewModel.getCurrentDaysExerciseInformation(
        userExerciseInformation?.exerciseInformation
    )

    Scaffold(
        bottomBar = { BottomBar(navController = navController)},
        topBar = {
            TopBar(exerciseInformation = currentDayExerciseInfo)
        },
        content = {
            Surface(

                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                color = AppBackGroundColor
            ) {
                LazyColumn(){
                    items(1){
                        InfoForDashboard(
                            exerciseInformation = currentDayExerciseInfo,
                            onStartExerciseClicked =
                            {
                                //update Notify hour time
                                val calendar: Calendar = Calendar.getInstance()
                                sharedViewModel.updateNotifyHourTime(newTime = calendar.get(Calendar.HOUR_OF_DAY),
                                    userName = sharedViewModel.signedUpUser)
                                navController.navigate(route = Screens.EXERCISES_SCREEN.name)
                            }
                        )
                    }
                }
            }
        }
        )
}

@Composable
private fun InfoForDashboard(
    exerciseInformation: ExerciseInformation?,
    onStartExerciseClicked: () -> Unit
)
{
    Column(
        modifier = Modifier.padding(top = TOP_PADDING_LARGE),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (exerciseInformation != null){
            if (exerciseInformation.day!= null && exerciseInformation.exerciseType != null){

                // UI For displaying exercise type.
                Text(
                    text = stringResource(id = getExerciseMap()[exerciseInformation.exerciseType!!]!!),
                    style = MaterialTheme.typography.h4,
                    color = Color.White, fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(bottom = PADDING_MEDIUM)
                )

                // UI for displaying total time information.
                val totalTime: Double = getTotalTimeFromListOfSubExercise(exerciseInformation.exercises)
                val hours: Int = getHours(totalTime*60)
                val minutes: Int = getMinutes(totalTime*60)
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_access_time_24),
                    contentDescription = "Timer icon",
                    tint = LighterAppThemeColor,
                    modifier = Modifier.padding(bottom = PADDING_SMALL)
                )
                TimeRepresentation(
                    hours = hours,
                    minutes = minutes,
                    modifier = Modifier.padding(bottom = PADDING_MEDIUM)
                )

                //Horizontal line UI.
                Divider(
                    color = LighterAppThemeColor,
                    thickness = SMALL_THICKNESS,
                    modifier = Modifier.padding(start = PADDING_MEDIUM, end = PADDING_MEDIUM)
                )

                //Button
                StartExerciseButton(onStartExerciseClicked = {onStartExerciseClicked()})
            }
        }

    }
}

@Composable
private fun StartExerciseButton(
    onStartExerciseClicked: () -> Unit
){
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = BIG_PADDING, end = BIG_PADDING, top = TOP_PADDING_LARGE),
        onClick = { onStartExerciseClicked() },
        colors = ButtonDefaults.buttonColors(AppThemeColor),
        shape = RoundedCornerShape(PADDING_NORMAL), elevation = ButtonDefaults.elevation(PADDING_NORMAL)
    )
    {
        Row {
            Text(
                text = stringResource(R.string.start_exercising),
                color = Color.White,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.weight(5f)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Right Arrow Button.",
                modifier = Modifier.weight(1f),
                tint = Color.White
            )
        }
    }
}

@Composable
private fun TimeRepresentation(
    hours: Int,
    minutes: Int,
    modifier: Modifier
){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier){
        if (hours<1){
            Text(text = String.format("%.0f",hours), style = MaterialTheme.typography.h4,color = Color.White)
            Text(text = stringResource(R.string.hour_option_1), style = MaterialTheme.typography.body1,color = Color.White)
        }
        else{
            Text(text = String.format("%d",hours), style = MaterialTheme.typography.h4,color = Color.White)
            Text(text = stringResource(R.string.hour_option_2), style = MaterialTheme.typography.body1,color = Color.White,
            )

            Text(text = String.format("%d",minutes), style = MaterialTheme.typography.h4, color = Color.White)
            Text(text = stringResource(R.string.minute_option), style = MaterialTheme.typography.subtitle2,color = Color.White,
            )
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavController
){
    val selectedIndex = remember { mutableStateOf(0)}
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = AppThemeColor,
    ) {

        // Navigation options.
        BottomNavigationItem(
            selected = selectedIndex.value == 0,
            onClick = {
                      selectedIndex.value = 0
                navController.navigate(route = Screens.DASHBOARD_SCREEN.name)
            },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Icon Showing Home icon.",tint = Color.White) },
            label = {Text(text = stringResource(R.string.home_navigation), color = Color.White)}
        )

        BottomNavigationItem(
            selected = selectedIndex.value == 1,
            onClick = {
                selectedIndex.value = 1;
                navController.navigate(route = Screens.INSIGHTS_SCREEN.name)
            },
            icon = { Image(painter = painterResource(id = R.drawable.ic_baseline_analytics_24), contentDescription = "Icon showing Analytics icon.")},
            label = { Text(text = stringResource(R.string.Insights_navigation),color = Color.White)}
        )

        BottomNavigationItem(
            selected = selectedIndex.value == 2,
            onClick = {
                selectedIndex.value = 2;
                navController.navigate(route = Screens.PROFILE_SCREEN.name)
            },
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Icon showing a person.", tint = Color.White)},
            label = {Text(text = stringResource(R.string.Profile_navigation),color = Color.White)}
        )
    }
}

@Composable
private fun TopBar(
    exerciseInformation: ExerciseInformation?
){
    if (exerciseInformation != null){
        if (exerciseInformation.day!= null){
            TopAppBar(
                title = { Box(modifier = Modifier.fillMaxWidth()){
                    if (exerciseInformation.day != null){
                        Text(text = stringResource(id = getDayMap()[exerciseInformation.day!!]!!), color = Color.White,
                            modifier = Modifier.align(Alignment.Center))
                    }
                }},
                backgroundColor = AppThemeColor)
        }
    }
}






