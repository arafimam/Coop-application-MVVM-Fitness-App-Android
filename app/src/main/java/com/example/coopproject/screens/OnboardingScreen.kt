package com.example.coopproject.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coopproject.R
import com.example.coopproject.model.UserExerciseInformation
import com.example.coopproject.model.UserInformation
import com.example.coopproject.navigation.Screens
import com.example.coopproject.ui.theme.*
import com.example.coopproject.utils.getOwnerNameFromEmail
import java.util.*

@Composable
fun OnBoardingScreen(
    sharedViewModel: SharedViewModel,
    navController: NavController,
    emailOfUser: String
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = AppBackGroundColor,
        topBar =
        {
            OnBoardingScreenTopAppBar (
                onBackClicked =
                {
                    navController.navigate(route = Screens.SIGNUP_SCREEN.name)
                }
            )
        },
        content = {
            OnBoardingScreenContent (onNextClicked = {
                gender,age,body ->
                sharedViewModel.signupUser(
                    UserInformation(userName = getOwnerNameFromEmail(email = emailOfUser)!!, email = emailOfUser, password = "DummyPassword",
                gender = gender, age = age.toInt(), bodyType = body)
                )

                sharedViewModel.insertUserExerciseInformation(
                    UserExerciseInformation(ownerName = getOwnerNameFromEmail(emailOfUser)!!,
                    exerciseInformation = sharedViewModel.dummyData, finishedWorkout = 0, unfinishedWorkout = 0)
                )
                navController.navigate(route = Screens.DASHBOARD_SCREEN.name)
            })

        }
    )

}

@Composable
fun OnBoardingScreenContent(
    onNextClicked: (String,String,String) -> Unit
)
{

    val selectedGender = remember {
        mutableStateOf("Male")
    }

    val age = remember {
        mutableStateOf(18)
    }

    val selectedHoursActive = remember {
        mutableStateOf("1 to 2")
    }



    val itemsForGender = listOf<String>("Male","Female")
    val itemsForHoursActive = listOf<String>("1 to 2","3 to 6","7+")
    val itemsForBodyType = listOf<String>("Obese","Lean","Athletic")
    val mapForBodyType = mapOf<String,String>(itemsForHoursActive[0] to itemsForBodyType[0],
        itemsForHoursActive[1] to itemsForBodyType[1],itemsForHoursActive[2] to itemsForBodyType[2])

    val selectedBodyType = remember {
        mutableStateOf(mapForBodyType[selectedHoursActive.value])
    }



    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(start = PADDING_MEDIUM, end = PADDING_MEDIUM, top = PADDING_MEDIUM)
    )
    {

            Text(
                text = "Gender",
                color = Color.White,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = PADDING_SMALL)
            )


        // Gender Stored in selectedValue.
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = BIG_PADDING)) {
            itemsForGender.forEach{
                Row(modifier = Modifier
                    .weight(6f)
                    .clickable {
                        selectedGender.value = it

                    }, verticalAlignment = Alignment.CenterVertically){
                    RadioButton(
                        modifier = Modifier.padding( end = 5.dp),
                        selected = selectedGender.value == it,
                        onClick = {
                            selectedGender.value = it
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = LighterAppThemeColor, unselectedColor = Color.White)
                    )
                    Text(text = it,color = Color.White, modifier = Modifier.padding(end = 10.dp))
                }

            }
        }

        // age.


        Text(
            text = "Age",
            color = Color.White,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = PADDING_SMALL))

        val mContext = LocalContext.current
        val mCalendar = Calendar.getInstance()

        val mYear = remember{
            mutableStateOf(mCalendar.get(Calendar.YEAR)-age.value.toInt())
        }

        val mMonth = remember { mutableStateOf(mCalendar.get(Calendar.MONTH)) }
        val mDay = remember { mutableStateOf(mCalendar.get(Calendar.DAY_OF_MONTH)) }

        mCalendar.time = Date()

        val mDate = remember {
            mutableStateOf("")
        }

        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate.value = "$mYear"
            }, mYear.value, mMonth.value, mDay.value
        )

        Card(modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = MINIMUM_TEXT_FIELD_HEIGHT, max = MAXIMUM_TEXT_FIELD_HEIGHT)
            .clickable {
                mDatePickerDialog.show()
            },
            backgroundColor = Color.White
        )
        {
            Column(verticalArrangement = Arrangement.Center){
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(text =  age.value.toString(),color = Color.Black,modifier = Modifier
                        .padding(start = PADDING_MEDIUM)
                        .weight(7f))
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_calendar_month_24), contentDescription = "Calendar Icon.",
                        modifier = Modifier.padding(end = PADDING_MEDIUM), tint = FadedTextColor)
                }
            }

        }

        if (mDate.value != ""){
            age.value = (mDate.value.toInt() - mCalendar.get(Calendar.YEAR))
            if (age.value.toInt() < 0){
                age.value = (age.value.toInt() * -1)
            }
        }

        // body type
        Text(
            text = "Body Type",
            color = Color.White,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = PADDING_SMALL))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = BIG_PADDING)) {
            itemsForHoursActive.forEach{
                Row(modifier = Modifier
                    .weight(6f)
                    .clickable {
                        selectedHoursActive.value = it

                    }, verticalAlignment = Alignment.CenterVertically){
                    RadioButton(
                        modifier = Modifier.padding(end = 5.dp),
                        selected = selectedHoursActive.value == it,
                        onClick = {
                            selectedHoursActive.value = it
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = LighterAppThemeColor, unselectedColor = Color.White)
                    )
                    Text(text = it,color = Color.White, modifier = Modifier.padding(end = 10.dp))
                }

            }
        }

        // Button
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(start = BIG_PADDING, end = BIG_PADDING),
            onClick = { onNextClicked(selectedGender.value,age.value.toString(),selectedBodyType.value.toString()) },
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
}

@Composable
fun OnBoardingScreenTopAppBar(
    onBackClicked: () -> Unit
){
    TopAppBar(
        modifier = Modifier.heightIn(min = MINIMUM_HEIGHT_TOP_APP_BAR, max = MAXIMUM_HEIGHT_TOP_APP_BAR),
        backgroundColor = AppThemeColor,
        contentColor = Color.White
    ){
        Box(modifier = Modifier.fillMaxWidth()){
            IconButton(onClick = { onBackClicked() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clearAndSetSemantics {
                        contentDescription =
                            "Back Button. Double Tap to navigate to Dashboard Screen."
                    })
            {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Button. Double Tap to navigate to Dashboard Screen.")
            }

            Text(text = "Registration", modifier = Modifier
                .align(Alignment.Center)
                , style = MaterialTheme.typography.h6)
        }
    }
}
