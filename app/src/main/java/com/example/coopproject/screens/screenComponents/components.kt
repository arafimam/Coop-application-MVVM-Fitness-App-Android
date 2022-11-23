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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coopproject.R
import com.example.coopproject.screens.SharedViewModel
import com.example.coopproject.ui.theme.*
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
){


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
            }else{
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

    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = PADDING_SMALL, end = PADDING_SMALL),
        shape = RoundedCornerShape(15.dp), backgroundColor = colorOfCard.value
    ){
        Column(
            verticalArrangement = Arrangement.Top
        ) {

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
                    .padding(top = PADDING_SMALL)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Top,
            ) {
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

                Image(painter = painterResource(id = imageState.value), contentDescription = "Pushup Image",
                modifier = Modifier
                    .fillMaxWidth().height(200.dp)
                    .padding(all = PADDING_SMALL))
                DoneButton(onDoneClicked = {
                    imageState.value = R.drawable.tickmark
                    colorOfCard.value = FadedTextColor
                    showExerciseData.value = false
                    colorOfDivider.value = IconColorForExerciseCard
                    Toast.makeText(context,"Completed $sets Sets of $exerciseType.",Toast.LENGTH_SHORT).show()
                    onClickedDone()
                },
                    stateOfButton = exerciseDoneState.value
                )
            }

        }
    }
}

@Composable
fun DoneButton(
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

    Button(
        modifier = Modifier
            .fillMaxWidth()
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
fun WeightCheckBox(
    sharedViewModel: SharedViewModel,
    getSelectedUnit: (String) -> Unit
){

    var expanded = remember {
        mutableStateOf(false)
    }
    // check country name.
    val nonMetricCountries: List<String> = listOf(stringResource(R.string.canada),
        stringResource(R.string.usa), stringResource(R.string.Myanmar), stringResource(R.string.liberia)
    )
    var items: List<String> = if (nonMetricCountries.contains(sharedViewModel.countryName)){
        listOf(stringResource(R.string.pounds), stringResource(R.string.kilograms))
    }
    else{
        listOf(stringResource(R.string.kilograms),stringResource(R.string.pounds))
    }

    var selectedIndex = remember {
        mutableStateOf(0)
    }
    getSelectedUnit(items[selectedIndex.value])

    Box(modifier = Modifier.clickable { expanded.value = true }){
        Row(verticalAlignment = Alignment.CenterVertically){
            Text(items[selectedIndex.value], color = Color.White)
            Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24), contentDescription = "Drop Down Button.",
                tint = Color.White)
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }) {

            items.forEachIndexed{index,s ->
                DropdownMenuItem(onClick = {
                    selectedIndex.value = index
                    expanded.value = false
                }) {
                    Text(text = s)
                }
            }

        }
    }
}

@Composable
fun HeightCheckBox(
    sharedViewModel: SharedViewModel,
    getHeightUnit: (String) -> Unit
){

    var expanded = remember {
        mutableStateOf(false)
    }
    // check country name.
    val nonMetricCountries: List<String> = listOf(stringResource(R.string.canada),
            stringResource(R.string.usa), stringResource(R.string.Myanmar), stringResource(R.string.liberia)
                )
    var items: List<String> = if (nonMetricCountries.contains(sharedViewModel.countryName)){
        listOf(stringResource(R.string.Foot), stringResource(R.string.meters))
    }
    else{
        listOf(stringResource(R.string.meters),stringResource(R.string.Foot))
    }

    var selectedIndex = remember {
        mutableStateOf(0)
    }
    getHeightUnit(items[selectedIndex.value])

    Box(modifier = Modifier.clickable { expanded.value = true }){
        Row(verticalAlignment = Alignment.CenterVertically){
            Text(items[selectedIndex.value], color = Color.White)
            Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24), contentDescription = "Drop Down Button.",
                tint = Color.White)
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }) {

            items.forEachIndexed{index,s ->
                DropdownMenuItem(onClick = {
                    selectedIndex.value = index
                    expanded.value = false
                }) {
                    Text(text = s)
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun View(){
    ExerciseRepresentation(exerciseType = "Push Up", reps = "3", sets = "4", image = R.drawable.inclinedchestpress,
        finished = false) {
    }
}



