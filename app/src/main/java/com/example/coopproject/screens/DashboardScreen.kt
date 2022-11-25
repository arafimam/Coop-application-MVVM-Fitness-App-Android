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
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

                // UI for displaying total time information.
                val totalTime: Double = getTotalTimeFromListOfSubExercise(exerciseInformation.exercises)
                val hours: Int = getHours(totalTime*60)
                val minutes: Int = getMinutes(totalTime*60)
                val contentDescriptionForTime = "$hours hours and $minutes minutes"
                val contentDescriptionForExerciseType = "Exercise type for ${exerciseInformation.day} is ${stringResource(id = getExerciseMap()[exerciseInformation.exerciseType!!]!!)}."
                Column(modifier = Modifier
                    .clearAndSetSemantics {
                        contentDescription = contentDescriptionForExerciseType + "Also, Total duration for exercise is" + contentDescriptionForTime
                    }
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally){

                    // UI For displaying exercise type.

                    Text(
                        text = stringResource(id = getExerciseMap()[exerciseInformation.exerciseType!!]!!),
                        style = MaterialTheme.typography.h4,
                        color = Color.White, fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .padding(bottom = PADDING_MEDIUM)
                            .clearAndSetSemantics {
                                contentDescription = contentDescriptionForExerciseType
                            }

                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_access_time_24),
                        contentDescription = stringResource(R.string.timerIcon),
                        tint = LighterAppThemeColor,
                        modifier = Modifier.padding(bottom = PADDING_SMALL)
                    )

                    TimeRepresentation(
                        hours = hours,
                        minutes = minutes,
                        modifier = Modifier
                            .padding(bottom = PADDING_MEDIUM)
                            .clearAndSetSemantics {
                                contentDescription = contentDescriptionForTime
                            }
                    )
                }

                //Horizontal line UI.
                Divider(
                    color = LighterAppThemeColor,
                    thickness = SMALL_THICKNESS,
                    modifier = Modifier.padding(start = PADDING_MEDIUM, end = PADDING_MEDIUM, bottom = TOP_PADDING_LARGE)
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
    val contentDescriptionsForStartExerciseButton = stringResource(id = R.string.contDescStartExerciseButton)
    Button(
        modifier = Modifier
            .clearAndSetSemantics {
                contentDescription = contentDescriptionsForStartExerciseButton
            }
            .fillMaxWidth().height(55.dp)
            .padding(start = BIG_PADDING, end = BIG_PADDING),
        onClick = { onStartExerciseClicked() },
        colors = ButtonDefaults.buttonColors(LighterAppThemeColor),
        shape = RoundedCornerShape(PADDING_NORMAL), elevation = ButtonDefaults.elevation(PADDING_NORMAL)
    )
    {
            Text(
                text = stringResource(R.string.start_exercising),
                color = Color.White,
                style = MaterialTheme.typography.h6,
            )
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
        val contentDescriptionsForDashboardNavigation: String = stringResource(R.string.cdForDashboardNav)
        val contentDescriptionForInsightsNavigation: String = stringResource(R.string.cdinsightsScreenNav)
        val contentDescriptionForProfileNavigation: String = stringResource(R.string.cdProfileScreenNav)
        // Navigation options.
        BottomNavigationItem(
            modifier = Modifier.clearAndSetSemantics { contentDescription = contentDescriptionsForDashboardNavigation },
            selected = selectedIndex.value == 0,
            onClick = {
                      selectedIndex.value = 0
                navController.navigate(route = Screens.DASHBOARD_SCREEN.name)
            },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Icon Showing Home icon.",tint = Color.White) },
            label = {Text(text = stringResource(R.string.home_navigation), color = Color.White)}
        )

        BottomNavigationItem(
            modifier = Modifier.clearAndSetSemantics { contentDescription = contentDescriptionForInsightsNavigation },
            selected = selectedIndex.value == 1,
            onClick = {
                selectedIndex.value = 1;
                navController.navigate(route = Screens.INSIGHTS_SCREEN.name)
            },
            icon = { Image(painter = painterResource(id = R.drawable.ic_baseline_analytics_24), contentDescription = "Icon showing Analytics icon.")},
            label = { Text(text = stringResource(R.string.Insights_navigation),color = Color.White)}
        )

        BottomNavigationItem(
            modifier = Modifier.clearAndSetSemantics { contentDescription = contentDescriptionForProfileNavigation },
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
                        val contentDescriptionForDay = stringResource(id = getDayMap()[exerciseInformation.day!!]!!)
                        Text(text = stringResource(id = getDayMap()[exerciseInformation.day!!]!!), color = Color.White,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .semantics {
                                    contentDescription = contentDescriptionForDay
                                })
                    }
                }},
                backgroundColor = AppThemeColor)
        }
    }
}






