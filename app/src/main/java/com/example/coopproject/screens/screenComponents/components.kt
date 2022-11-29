package com.example.coopproject.screens.screenComponents

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coopproject.R
import com.example.coopproject.screens.SharedViewModel
import com.example.coopproject.ui.theme.*
import com.example.coopproject.utils.getContentDescriptionOfImage
import com.example.coopproject.utils.getSubExerciseMap

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppInputTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    maxLine: Int = 1,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit = {},
    bgColor: Color = Color.White,
    textColor: Color = Color.Black
){
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        //modifier = modifier.background(Color.White, RoundedCornerShape(5.dp)),
        modifier =  modifier,
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(textColor = textColor, backgroundColor = bgColor,
        focusedIndicatorColor = LighterAppThemeColor, unfocusedIndicatorColor = AppThemeColor,
        focusedLabelColor = LighterAppThemeColor, unfocusedLabelColor = AppThemeColor),
        maxLines = maxLine,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        trailingIcon = {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_account_box_24), contentDescription = "Profile Icon",
            tint = FadedTextColor)
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppInputNumberField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    maxLine: Int = 1,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit = {},
    textColor: Color = Color.Black,
    indicatorColor: Color = AppThemeColor
){
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        //modifier = modifier.background(Color.White, RoundedCornerShape(5.dp)),
        modifier = modifier,
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(textColor = textColor, backgroundColor = Color.White,
            focusedIndicatorColor = LighterAppThemeColor, unfocusedIndicatorColor = indicatorColor,
            focusedLabelColor = LighterAppThemeColor, unfocusedLabelColor = AppThemeColor),
        maxLines = maxLine,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
    )
}

@Composable
fun ExerciseRepresentation(
    exerciseType: String,
    reps: String,
    sets: String,
    image: Int,
    finished: Boolean,
    onClickedDone: () -> Unit
)
{
    val exerciseDoneState = remember{
        mutableStateOf(finished)
    }

    val imageState = remember {
        mutableStateOf(
            if (!exerciseDoneState.value){
                image
            }
        else{
            R.drawable.tickmark
            }
        )
    }

    val showExerciseData = remember{
        mutableStateOf(
            !exerciseDoneState.value
        )
    }

    val colorOfCard = remember {
        mutableStateOf(
            if (!exerciseDoneState.value) {
                ExerciseCardColor
            }
            else{
                FadedTextColor
            }
        )
    }

    val colorOfDivider = remember {
        mutableStateOf(
            if (!exerciseDoneState.value) {
                FadedTextColor
            }else{
                IconColorForExerciseCard
            }
        )
    }
    val exName = stringResource(id = getSubExerciseMap()[exerciseType]!!)
    val contentDescForExerciseDescWhenNotDone = "Exercise name is ${stringResource(id = getSubExerciseMap()[exerciseType]!!)}. Number of repetitions are $reps and number of sets are $sets."
    val contentDescForExerciseDescWhenDone = "Completed $exName."

    val contentDesc = remember {
        mutableStateOf(
            if (!exerciseDoneState.value){
                contentDescForExerciseDescWhenNotDone
            }else{
                contentDescForExerciseDescWhenDone
            }
        )
    }

    val contentDescOfImage = remember {
        mutableStateOf(
            if (!exerciseDoneState.value){
                getContentDescriptionOfImage(exName)
            }else{
                "Image showing a green tick."
            }
        )
    }

    val contentDescForDoneButton = remember {
        mutableStateOf(
            if (!exerciseDoneState.value){
                "Finish exercise."
            }else{
                ""
            }
        )
    }

    val doneButtonDisability = remember {
        mutableStateOf(!exerciseDoneState.value)
    }

    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = PADDING_SMALL, end = PADDING_SMALL),
        shape = RoundedCornerShape(15.dp), color = colorOfCard.value
    ){
        Column(
            verticalArrangement = Arrangement.Top
        )
        {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = contentDesc.value }
            ){
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = PADDING_SMALL, start = PADDING_SMALL, end = PADDING_SMALL)) {
                    Text(text = stringResource(id = getSubExerciseMap()[exerciseType]!!),color = Color.Black,
                        modifier = Modifier.padding(end = PADDING_SMALL))
                    Divider(
                        color = colorOfDivider.value,
                        thickness = SMALL_THICKNESS,
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = BIG_PADDING),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    if (showExerciseData.value){
                        Row(modifier = Modifier.padding(start = 80.dp)){

                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(4f)) {
                                Icon(painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                                    contentDescription = "Repetitions",
                                    tint = IconColorForExerciseCard)
                                Text(text = "$reps ${stringResource(id = R.string.reps)}", color = Color.Black)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.weight(4f)) {
                                Icon(painter = painterResource(id = R.drawable.ic_baseline_donut_large_24),
                                    contentDescription = "Sets",
                                    tint = IconColorForExerciseCard)
                                Text(text = "$sets ${stringResource(id = R.string.sets)}", color = Color.Black)
                            }
                        }
                    }

                }

            }

            Column(
                modifier = Modifier
                    .padding(top = PADDING_SMALL)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Top,
            )
            {
                Image(painter = painterResource(id = imageState.value), contentDescription = contentDescOfImage.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(all = PADDING_SMALL))
                DoneButton(onDoneClicked = {
                    doneButtonDisability.value = false
                    contentDescForDoneButton.value = ""
                    contentDescOfImage.value = "Image showing a green tick."
                    contentDesc.value = contentDescForExerciseDescWhenDone
                    imageState.value = R.drawable.tickmark
                    colorOfCard.value = FadedTextColor
                    showExerciseData.value = false
                    colorOfDivider.value = IconColorForExerciseCard
                    Toast.makeText(context,"Completed $sets Sets of $exerciseType.",Toast.LENGTH_SHORT).show()
                    onClickedDone()
                },
                    stateOfButton = exerciseDoneState.value,
                    contentDesc = contentDescForDoneButton.value,
                    enable = doneButtonDisability.value
                )

            }
        }
    }
}

@Composable
fun DoneButton(
    enable: Boolean,
    contentDesc: String,
    onDoneClicked: () -> Unit,
    stateOfButton: Boolean
    ){

    val colorOfButton  = remember {
        mutableStateOf(
            if (!stateOfButton){
                IconColorForExerciseCard
            }else{
                FadedTextColor
            }
        )
    }

    val testTagDoneButton = stringResource(R.string.TestTagDoneButtonExercise)
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = contentDesc
                testTag = testTagDoneButton
            }
            .padding(
                start = BIG_PADDING,
                end = BIG_PADDING,
                top = PADDING_SMALL,
                bottom = PADDING_SMALL
            ),
        onClick = {
            onDoneClicked()
            colorOfButton.value = FadedTextColor
                  },
        colors = ButtonDefaults.buttonColors(colorOfButton.value),
        shape = RoundedCornerShape(PADDING_NORMAL), elevation = ButtonDefaults.elevation(PADDING_NORMAL),
        enabled = enable
    ) {
        Box(){
            Text(
                text = stringResource(id = R.string.DoneButtonText),
                color = Color.White,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun UnitSelector(
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel,
    getUnitSelected: (String) -> Unit
){
    var selectedIndex = remember {
        mutableStateOf(0)
    }

    val nonMetricCountries: List<String> = listOf(stringResource(R.string.canada),
        stringResource(R.string.usa), stringResource(R.string.Myanmar), stringResource(R.string.liberia)
    )
    var items: List<String> = if (nonMetricCountries.contains(sharedViewModel.countryName)){
        listOf("Imperial Units","Metric Units")

    }
    else{
        listOf("Metric Units", "Imperial Units")
    }


    getUnitSelected(items[selectedIndex.value])

    val colorOfSelected = IconColorForExerciseCard
    val colorOfNotSelected = FadedTextColor

    // pre selected.
    val colorOfButton1 = remember{
        mutableStateOf(colorOfSelected)
    }

    //pre unselected.
    val colorOfButton2 = remember{
        mutableStateOf(colorOfNotSelected)
    }
    val testTagButton1 = stringResource(R.string.TestTagButton1)
    val testTagButton2 = stringResource(R.string.TestTagButton2)

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = PADDING_MEDIUM)
            ){

        Button(onClick = {
        colorOfButton1.value = colorOfSelected
        colorOfButton2.value = colorOfNotSelected
            selectedIndex.value = 0
        }, shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(colorOfButton1.value),
            modifier = Modifier.padding(start = BIG_PADDING, end = PADDING_SMALL).clearAndSetSemantics {
                contentDescription = "Button For ${items[0]}. Double tap to select."
                testTag = testTagButton1
            }
            ) {

            Text(text = items[0])
        }

        Button(onClick = {
            colorOfButton1.value = colorOfNotSelected
            colorOfButton2.value = colorOfSelected
            selectedIndex.value = 1
        },
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(colorOfButton2.value),
            modifier = Modifier.padding(start = PADDING_SMALL, end = PADDING_MEDIUM).clearAndSetSemantics {
                contentDescription = "Button For ${items[1]}. Double tap to select."
                testTag = testTagButton2
            }
        ) {
            Text(text = " ${items[1]} ")
        }

    }
}

@Composable
fun ParameterWidget(
    parameterType: String,
    parameterUnit: String,
    getParameterValue: (Float) -> Unit,
    parameterDefaultValue: Float,
    parameterMinValue: Float,
    parameterMaxValue: Float
){
    val parameterValue = remember{
        mutableStateOf(parameterDefaultValue)
    }
    getParameterValue(parameterValue.value)
    if (parameterValue.value > parameterMaxValue){
        Toast.makeText(LocalContext.current,"$parameterType cannot be more than $parameterMaxValue",Toast.LENGTH_SHORT).show()
        parameterValue.value = parameterDefaultValue
    }

    if (parameterValue.value < 0){
        Toast.makeText(LocalContext.current,"$parameterType cannot be less than 0.",Toast.LENGTH_SHORT).show()
        parameterValue.value = parameterDefaultValue
    }

    val testTagForUnit = stringResource(R.string.TestTagUnit)
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
        ){
            Column(modifier = Modifier
                .fillMaxWidth()
                .clearAndSetSemantics {
                    contentDescription = "$parameterType in $parameterUnit"
                    testTag = testTagForUnit
                    text = AnnotatedString(parameterUnit)
                                      },
            horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = parameterType, color = Color.White,
                    style = MaterialTheme.typography.h4)
                Text(text = parameterUnit, color = FadedTextColor,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
            }


            val TestTagSubtractButton = stringResource(R.string.TestTagSubtractButton)
            Row(
                modifier = Modifier.padding(top = PADDING_SMALL)
            ) {
                IconButton(onClick = {
                    parameterValue.value = parameterValue.value - 1
                },modifier = Modifier.semantics{
                    contentDescription = "Subtract Value Icon. Double tap to reduce $parameterType by 1"
                    testTag = TestTagSubtractButton
                                               },
                    enabled = parameterValue.value > parameterMinValue
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_remove_circle_24),
                        contentDescription = "",
                        tint = Color.White, modifier = Modifier.size(45.dp))
                }
                val testTagForParameter = stringResource(R.string.TestTagForParameterValue)
                AppInputNumberField(text = String.format("%.1f",parameterValue.value), label = "", onTextChange = {
                    if (it != ""){
                        parameterValue.value = it.toFloat()
                    }
                },
                    modifier = Modifier
                        .width(80.dp)
                        .clearAndSetSemantics {
                            text = AnnotatedString("${parameterValue.value}")
                            testTag = testTagForParameter
                            contentDescription =
                                "Current $parameterType value is ${parameterValue.value}. Double tap to edit by using the Edit Text Field"
                        },
                    textColor = Color.White
                )
                //Text(text = weightValue.value.toString(),color = Color.White, style = MaterialTheme.typography.h4,)
                val testTagForPlusButton = stringResource(R.string.TestTagPlusButton)
                IconButton(onClick = {
                    parameterValue.value = parameterValue.value + 1
                },modifier = Modifier.semantics {
                    contentDescription = "Add Value Icon. Double tap to add $parameterType by 1"
                    testTag = testTagForPlusButton
                                                },
                    enabled = parameterValue.value < parameterMaxValue
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_add_circle_24),
                        contentDescription = "",
                        tint = Color.White,modifier = Modifier.size(45.dp))
                }


            }
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun PreView(){
    UnitSelector(modifier = Modifier, sharedViewModel = viewModel(), getUnitSelected = {})
}



