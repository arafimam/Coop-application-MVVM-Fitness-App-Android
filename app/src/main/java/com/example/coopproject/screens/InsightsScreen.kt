package com.example.coopproject.screens

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coopproject.navigation.Screens
import com.example.coopproject.R
import com.example.coopproject.ui.theme.*
import com.example.coopproject.utils.calculateUserPercentage
import com.example.coopproject.utils.calculateUserPoints

@Composable
fun InsightsScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
)
{

    // fetch user exercise information.
    val userExerciseInformation by sharedViewModel.userExerciseInfoData.collectAsState()

    Scaffold (
        topBar = {
            TopBarInsightScreen (onBackClicked = {navController.navigate(route = Screens.DASHBOARD_SCREEN.name)})
        },
        content = {
            Surface (
                modifier = Modifier.fillMaxSize(),
                color = AppBackGroundColor
                    ){
                Column {
                    WelcomeText(userName = userExerciseInformation?.ownerName)
                    PointsWheel(userExerciseInformation?.finishedWorkout,userExerciseInformation?.unfinishedWorkout)
                    WorkoutInformation(finishWorkout = userExerciseInformation?.finishedWorkout, unfinishedWorkout = userExerciseInformation?.unfinishedWorkout)
                    BodyTypeButton(onBodyTypeButtonClicked = {navController.navigate(route = Screens.BMI_SCREEN.name)})
                }
            }
        }
    )

}

@Composable
private fun BodyTypeButton(
    onBodyTypeButtonClicked: () -> Unit
)
{
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = BIG_PADDING, end = BIG_PADDING, top = PADDING_NORMAL),
        onClick = { onBodyTypeButtonClicked() },
        colors = ButtonDefaults.buttonColors(LighterAppThemeColor),
        shape = RoundedCornerShape(PADDING_NORMAL), elevation = ButtonDefaults.elevation(PADDING_NORMAL)) {
        Row {
            Text(
                text = stringResource(R.string.bodyTypeButtonText),
                color = Color.White,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.weight(5f)
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Right Arrow Button.",
                modifier = Modifier.weight(1f),
                tint = Color.White)
        }
    }
}

@Composable
fun WorkoutInformation(
    finishWorkout: Int?,
    unfinishedWorkout: Int?
)
{
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(start = PADDING_NORMAL, end = PADDING_NORMAL, top = PADDING_NORMAL),
    backgroundColor = CardColor,
    shape = RoundedCornerShape(15.dp)) {
        Column() {
            WorkoutTextInfo(info = stringResource(R.string.daysWorkedOut), value = finishWorkout!!)
            WorkoutTextInfo(info = stringResource(R.string.daysNotWorkedout), value = unfinishedWorkout!!)
        }
        
    }

}

@Composable
private fun WorkoutTextInfo(
    info: String,
    value: Int
)
{
    Box(modifier = Modifier.padding(top = 30.dp),
    contentAlignment = Alignment.Center){
        Row(modifier = Modifier.padding(start = PADDING_NORMAL, end = PADDING_NORMAL)){
            Text(text = "$info", modifier = Modifier
                .weight(7f),
                color = Color.White,
                style = MaterialTheme.typography.body1)
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .height(30.dp),
                backgroundColor = WorkoutTextBackgroundColor,
                shape = RoundedCornerShape(15.dp)
            ){
                Box(contentAlignment = Alignment.Center){
                    Text(text = String.format("%d",value),style = MaterialTheme.typography.body1)
                }
            }
        }

    }

}

@Composable
private fun WelcomeText(
    userName: String?
)
{
    // Welcome Text: Hello <username> 'hand_wave' \n This is your progress
    if (userName!= null){
        Column(modifier = Modifier.padding(top = BIG_PADDING, start = BIG_PADDING)) {
            Row(modifier = Modifier.padding(bottom = SMALL_THICKNESS)) {
                Text(text = "${stringResource(id = R.string.greeting1)} $userName, ",
                    color = Color.White,
                    style = MaterialTheme.typography.body1
                )
                Icon(painter = painterResource(id = R.drawable.ic_baseline_front_hand_24),
                    contentDescription = "Hand waving.",
                    tint = HandColor,
                    modifier = Modifier
                        .rotate(15f)
                        .height(20.dp))
            }
            Text(text = stringResource(R.string.greeting2),color = Color.White,style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
private fun PointsWheel(
    finishWorkout: Int?,
    unfinishedWorkout: Int?
)
{
    Card(
        backgroundColor = CardColor,
        modifier = Modifier.padding(top = PADDING_MEDIUM, start = BIG_PADDING, end = BIG_PADDING)
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(15.dp)
    )
    {
        val totalScore: String = String.format("%d",5)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            CustomizedProgressWheel(
                percentage =
                calculateUserPercentage(finishedNumber = finishWorkout,
                    unfinishedNumber = unfinishedWorkout),
                number = 100,
                animDuration = 2500,
                text = "${calculateUserPoints(finishedNumber = finishWorkout, 
                    unfinishedNumber = unfinishedWorkout)}/$totalScore")
        }
    }

}

@Composable
private fun CustomizedProgressWheel(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 20.sp,
    radius: Dp = 75.dp,
    color: Color = LighterAppThemeColor,
    animDuration: Int,
    animDelay: Int = 0,
    text: String
)
{
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val currentPercentage = animateFloatAsState(
        targetValue = if(animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )

    LaunchedEffect(key1 = true){
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius*2f)
    ){
        Canvas(modifier = Modifier.size(radius*2f)){
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(cap = StrokeCap.Round, width = 20f)
            )
        }
        Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = text,
                color = Color.White, fontSize = fontSize)
            Text(text = stringResource(R.string.points_text),color = FadedTextColor, style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold)
        }

    }


}

@Composable
private fun TopBarInsightScreen(
    onBackClicked: () -> Unit
){
    TopAppBar(
        backgroundColor = AppThemeColor,
        content = {
            IconButton(onClick = { onBackClicked() }) {
                Icon(imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Button.",
                tint = Color.White)
            }
        }
    )
}
