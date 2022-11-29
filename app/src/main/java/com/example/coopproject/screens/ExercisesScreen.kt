package com.example.coopproject.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coopproject.R
import com.example.coopproject.model.UserExerciseInformation
import com.example.coopproject.navigation.Screens
import com.example.coopproject.screens.screenComponents.ExerciseRepresentation
import com.example.coopproject.ui.theme.*
import com.example.coopproject.utils.ExerciseInformation
import com.example.coopproject.utils.getDayMap
import com.example.coopproject.utils.getExerciseMap
import com.example.coopproject.utils.getImageBasedOnExercise

@Composable
fun ExercisesScreen(navController: NavController,sharedViewModel: SharedViewModel){

    val userExerciseInformation by sharedViewModel.userExerciseInfoData.collectAsState()
    val currentDayExerciseInfo: ExerciseInformation? = sharedViewModel.getCurrentDaysExerciseInformation(
        userExerciseInformation?.exerciseInformation)
    val showAlert = remember{
        mutableStateOf(false)
    }

    Scaffold (
        topBar = {
            TopAppBar() {
                TopAppBar(
                    backgroundColor = AppBackGroundColor,
                    contentColor = Color.White
                ) {
                    Box(modifier = Modifier.fillMaxWidth()){
                        IconButton(onClick = { navController.navigate(Screens.DASHBOARD_SCREEN.name) }, modifier = Modifier.align(
                            Alignment.CenterStart)) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Button")
                        }
                        Text(text = stringResource(id = getDayMap()[currentDayExerciseInfo?.day!!]!!), style = MaterialTheme.typography.h6, modifier = Modifier.align(
                            Alignment.Center))
                    }
                }
            }
        },
        content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = AppBackGroundColor
            ) {
                if (showAlert.value){
                    AlertDialogForUnfinishedWorkout(onConfirmClicked = {
                        if (userExerciseInformation != null){
                            sharedViewModel.updateUnfinishedWorkoutValue(unfinishedWorkoutValue = userExerciseInformation!!.unfinishedWorkout+1,
                                id = userExerciseInformation!!.id)
                        }

                        showAlert.value = false
                        navController.navigate(route = Screens.DASHBOARD_SCREEN.name)
                    }, onDismissal = {
                        showAlert.value = false
                    })
                }
                Card(
                    modifier = Modifier.fillMaxSize(),
                    backgroundColor = LighterAppThemeColor,
                    shape = RoundedCornerShape(topStart = PADDING_SMALL, topEnd = PADDING_SMALL)
                ){
                    if (currentDayExerciseInfo != null && userExerciseInformation != null){
                        exerciseContent(
                            currentDayExercise = currentDayExerciseInfo,
                            alert = showAlert
                        )
                    }
                }
            }
        }
            )
}

@Composable
fun exerciseContent(
    currentDayExercise: ExerciseInformation,
    alert: MutableState<Boolean>
)
{
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = PADDING_SMALL, start = PADDING_SMALL, end = PADDING_SMALL)) {
            Text(text = stringResource(id = getExerciseMap()[currentDayExercise.exerciseType!!]!!),color = Color.White,
            modifier = Modifier.padding(end = PADDING_SMALL), style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold)
            Divider(color = Color.White,
                thickness = SMALL_THICKNESS)
        }
        Spacer(modifier = Modifier.height(PADDING_SMALL))
        LazyColumn(){
            items(currentDayExercise.exercises){
                exercises ->

                ExerciseRepresentation(
                    exerciseType = exercises.nameOfSubExercise!!,
                    reps = String.format("%d",exercises.reps),
                    sets = String.format("%d",exercises.sets) ,
                    image = getImageBasedOnExercise(exerciseName = exercises.nameOfSubExercise!!),
                    finished = exercises.finished ,
                    onClickedDone = {
                        exercises.finished = true
                    }
                )
                Spacer(modifier = Modifier.height(PADDING_SMALL))
            }
            items(1){
                FinishedButton (onStartExerciseClicked = {
                    var checkAllExerciseDone: Boolean = true
                    for (i in 0 until currentDayExercise.exercises.size){
                        if (!currentDayExercise.exercises[i].finished){
                            alert.value = true
                            checkAllExerciseDone = false
                        }
                    }
                })
            }
        }




    }
}

@Composable
fun AlertDialogForUnfinishedWorkout(
    onConfirmClicked: () -> Unit,
    onDismissal: () -> Unit
)
{
    AlertDialog(
        title = { Text(text = stringResource(R.string.exerciseAlertTitle))},
        text = { Text(text = stringResource(R.string.exerciseAlertBody))},
        onDismissRequest = { onDismissal() },
        confirmButton = {
            TextButton(onClick = { onConfirmClicked() }){
                Text(text = stringResource(R.string.Button2))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissal() }) {
                Text(text = stringResource(R.string.Button1))
            }
        }
    )
}

@Composable
fun FinishedButton(
    onStartExerciseClicked: () -> Unit){
    val testTagFinishExerciseButton = stringResource(R.string.TestTagFinishExerciseButton)
    Button(
        modifier = Modifier
            .fillMaxWidth().height(55.dp).testTag(testTagFinishExerciseButton)
            .padding(start = BIG_PADDING, end = BIG_PADDING),
        onClick = { onStartExerciseClicked() },
        colors = ButtonDefaults.buttonColors(AppThemeColor),
        shape = RoundedCornerShape(PADDING_NORMAL), elevation = ButtonDefaults.elevation(PADDING_NORMAL)) {

            Text(
                text = stringResource(R.string.finishWorkoutButtonText),
                color = Color.White,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.weight(5f)
            )

    }
}