package com.example.coopproject.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coopproject.R
import com.example.coopproject.model.UserInformation
import com.example.coopproject.navigation.Screens
import com.example.coopproject.screens.screenComponents.AppInputTextField
import com.example.coopproject.ui.theme.*
import java.util.*

@Composable
fun ProfileScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
)
{

    LaunchedEffect(key1 = true){
        sharedViewModel.getUserInformation(sharedViewModel.signedUpUser!!)
    }
    val userInfo by sharedViewModel.userInformation.collectAsState()
    val username = remember {
        mutableStateOf(userInfo?.userName)
    }
    val context = LocalContext.current
    val age = remember {
        mutableStateOf(userInfo?.age.toString())
    }
    val bodyType = remember {
        mutableStateOf(userInfo?.bodyType)
    }
    val showAlert = remember {
        mutableStateOf(false)
    }
    val cancelledInAlertClicked = remember{
        mutableStateOf(false)
    }
    val showDoneButton = remember {
        mutableStateOf(false)
    }
    Scaffold (
        topBar = {
                 ProfileScreenTopBar (
                     onBackClicked = {navController.navigate(Screens.DASHBOARD_SCREEN.name)},
                     onDoneClicked = {
                         if (username.value!!.isNotEmpty() && age.value.isNotEmpty() && bodyType.value!!.isNotEmpty()){
                             showAlert.value = true
                         }
                         else{
                             Toast.makeText(context,"All Fields are not Filled.",Toast.LENGTH_SHORT).show()
                         }
                                     },
                     showDoneButton = showDoneButton.value
                 )
        },
        bottomBar = {
                    ProfileScreenBottomBar()
        },
        content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = AppBackGroundColor
            ){
                ProfileScreenAlertDialog(show = showAlert.value,
                    onConfirmClicked = {
                            sharedViewModel.updateUserInfo(name = username.value!!, age = age.value.toInt(),
                                bodyType = bodyType.value!!, id = userInfo?.id!!)
                        //TODO: Here we are updating with dummy data for exercise info.
                        sharedViewModel.updateUserExerciseInformation(name = username.value!!,
                            exerciseInfo = sharedViewModel.dummyData, id = userInfo?.id!!)

                        // TODO: update the signed up user name.
                        sharedViewModel.signedUpUser = username.value!!

                        showAlert.value = false
                        navController.navigate(Screens.DASHBOARD_SCREEN.name)
                                       },
                    onDismissal = {
                        cancelledInAlertClicked.value = true
                        showAlert.value = false
                    })

                    ProfileScreenContent(userInfo = userInfo, onUserNameChanged = {
                        username.value = it
                    },
                        onAgeChanged = {age.value = it},
                        onBodyTypeChanged = { bodyType.value = it },
                        cancelClicked = cancelledInAlertClicked,
                        onEditMade = {showDoneButton.value = it.value}
                    )
            }
        }
            )
}

@Composable()
private fun ProfileScreenAlertDialog(
    show: Boolean,
    onConfirmClicked: () -> Unit,
    onDismissal: () -> Unit
)
{
    if (show){
        AlertDialog(
            title = { Text(text = stringResource(R.string.profileAlertTitle))},
            text = { Text(text = stringResource(R.string.profileAlertBody))},
            onDismissRequest = { onDismissal() },
            confirmButton = {
                TextButton(onClick = { onConfirmClicked() }){
                    Text(text = stringResource(id = R.string.Button2))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismissal() }) {
                    Text(text = stringResource(id = R.string.Button1))
                }
            }
        )

    }
}

@Composable
fun ProfileScreenBottomBar()
{

    val contentDescriptionForBottomAppBar = stringResource(R.string.warningText1Profile) +" "+ stringResource(R.string.warningText2Profile)
    val testTagMsg = stringResource(R.string.TestTagProfileScreenBottomBar)
    Card(modifier = Modifier
        .fillMaxWidth()
        .clearAndSetSemantics {
            contentDescription = contentDescriptionForBottomAppBar
            testTag = testTagMsg
        }
        .height(125.dp),
        shape = RoundedCornerShape(topEnd = TOP_PADDING_LARGE, topStart = TOP_PADDING_LARGE),
        backgroundColor = AppThemeColor){
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                Column(verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally){
                    Text(
                        text = stringResource(R.string.warningText1Profile),
                        color = Color.White,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = SMALL_THICKNESS)
                    )
                    Text(
                        text = stringResource(R.string.warningText2Profile),
                        color = Color.White,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }

}

@Composable
fun ProfileScreenContent(
    cancelClicked : MutableState<Boolean>,
    userInfo: UserInformation?,
    onUserNameChanged: (String) -> Unit,
    onAgeChanged: (String) -> Unit,
    onBodyTypeChanged: (String) -> Unit,
    onEditMade: (MutableState<Boolean>) -> Unit
)
{
    if (userInfo != null){

        val editMade = remember{
            mutableStateOf(false)
        }
        onEditMade(editMade)

        val username = remember {
            mutableStateOf(userInfo.userName)
        }

        val age = remember {
            mutableStateOf(userInfo.age.toString())
        }

        val bodyType = remember {
            mutableStateOf(userInfo.bodyType)
        }

        var selectedValue = remember {
            mutableStateOf(userInfo.bodyType)
        }

        if (cancelClicked.value){
            username.value = userInfo.userName
            age.value = userInfo.age.toString()
            bodyType.value = bodyType.value
            cancelClicked.value = false
        }

        onUserNameChanged(username.value)
        onAgeChanged(age.value)
        onBodyTypeChanged(selectedValue.value)

        val itemsForBodyType = listOf<String>("${String.format("%d",1)} to ${String.format("%d",2)}"
            ,"${String.format("%d",3)} to ${String.format("%d",6)}","${String.format("%d",7)}+")
        val activityToBodyType: Map<String,String> = mapOf("Obese" to itemsForBodyType[0] ,
            "Lean" to itemsForBodyType[1],"Athletic" to itemsForBodyType[2])
        val bodyTypeToActivity: Map<String,String> = mapOf( itemsForBodyType[0] to "Obese" ,
            itemsForBodyType[1] to "Lean",itemsForBodyType[2] to "Athletic")

        Column (
            modifier = Modifier
                .padding(top = TOP_PADDING_LARGE, start = BIG_PADDING, end = BIG_PADDING)
                .verticalScroll(rememberScrollState(), reverseScrolling = true)
        )
        {
            Column {
                Text(
                    text = stringResource(R.string.userNameHeading),
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = PADDING_SMALL)
                )
                val contentDescriptionForUserName = "Current user name is ${username.value}. Double tap to Edit."
                val testTagForUserNameTextField = stringResource(R.string.TestTagUserNameTextField)
                AppInputTextField(text = username.value, label = "User name", onTextChange = {
                    username.value = it
                    editMade.value = true
                },
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(5.dp)).fillMaxWidth().
                            heightIn(min = MINIMUM_TEXT_FIELD_HEIGHT, max = MAXIMUM_TEXT_FIELD_HEIGHT)
                        .clearAndSetSemantics {
                            testTag = "userName"
                            contentDescription = contentDescriptionForUserName
                            text = AnnotatedString("${username.value}")
                        }
                )
                Divider(color = LighterAppThemeColor,
                    thickness = SMALL_THICKNESS,
                    modifier = Modifier.padding(top = PADDING_MEDIUM, bottom = PADDING_MEDIUM) )
                /**
                 * Radio button row
                 */
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    Text(
                        text = stringResource(R.string.hoursActiveHeading),
                        color = Color.White,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = PADDING_SMALL)
                    )
                }

                val testTageBodyType = stringResource(R.string.TestTagRadioButton)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = BIG_PADDING)) {
                      itemsForBodyType.forEach{
                          Row(modifier = Modifier
                              .weight(6f)
                              .clearAndSetSemantics {
                                  contentDescription = "Radio Button option for $it hours."
                                  stateDescription =
                                      if (activityToBodyType[selectedValue.value] == it) {
                                          "Radio Button for ${it} hours is Selected."
                                      } else {
                                          "Radio Button for ${it} hours not selected."
                                      }
                                  testTag = testTageBodyType
                                  text = AnnotatedString("${selectedValue.value}")
                              }
                              .clickable {
                                  selectedValue.value = bodyTypeToActivity[it]!!
                                  if (it != userInfo.bodyType) {
                                      editMade.value = true
                                  }
                              }){
                              RadioButton(
                                  modifier = Modifier.padding(end = 5.dp),
                                  selected = activityToBodyType[selectedValue.value] == it,
                                  onClick = {
                                      selectedValue.value = bodyTypeToActivity[it]!!
                                      if (it != userInfo.bodyType){
                                          editMade.value = true
                                      }
                                            },
                                  colors = RadioButtonDefaults.colors(selectedColor = LighterAppThemeColor, unselectedColor = Color.White)
                              )
                              Text(text = it,color = Color.White, modifier = Modifier.padding(end = 10.dp))
                          }

                      }
                }

                val contentDescriptionForAgeHeading = stringResource(R.string.ageHeading)
                Text(
                    text = stringResource(R.string.ageHeading),
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = PADDING_SMALL)
                        .semantics {
                            contentDescription = contentDescriptionForAgeHeading
                        }
                )

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

                val testTagAge = stringResource(R.string.TestTagAge)
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = MINIMUM_TEXT_FIELD_HEIGHT,max = MAXIMUM_TEXT_FIELD_HEIGHT)
                    .clearAndSetSemantics {
                        contentDescription =
                            "Current age is ${age.value}. Double tap to set of date of birth."
                        testTag = testTagAge
                        text = AnnotatedString("${age.value}")

                    }
                    .clickable {
                        mDatePickerDialog.show()
                        editMade.value = true
                    },
                    backgroundColor = Color.White
                    )
                {
                    Column(verticalArrangement = Arrangement.Center){
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Text(text =  age.value,color = Color.Black,modifier = Modifier
                                .padding(start = PADDING_MEDIUM)
                                .weight(7f))
                            Icon(painter = painterResource(id = R.drawable.ic_baseline_calendar_month_24), contentDescription = "Calendar Icon.",
                            modifier = Modifier.padding(end = PADDING_MEDIUM), tint = FadedTextColor)
                        }
                    }

                }

                if (mDate.value != ""){
                    age.value = (mDate.value.toInt() - mCalendar.get(Calendar.YEAR)).toString()
                    if (age.value.toInt() < 0){
                        age.value = (age.value.toInt() * -1).toString()
                    }
                }
            }
            


        }
    }
}

@Composable
fun ProfileScreenTopBar(
    showDoneButton: Boolean,
    onBackClicked: () -> Unit,
    onDoneClicked: () -> Unit
)
{
    val contentDescriptionForDoneButton = stringResource(R.string.cdDoneButton)
    val backButtonTag = stringResource(id = R.string.TestTagBackButton)
    val doneButtonTag = stringResource(R.string.TestTagDoneButton)
    TopAppBar(
        modifier = Modifier.heightIn(min = MINIMUM_HEIGHT_TOP_APP_BAR, max = MAXIMUM_HEIGHT_TOP_APP_BAR),
        backgroundColor = AppThemeColor,
        contentColor = Color.White
    ) {
        Box(modifier = Modifier.fillMaxWidth()){
            IconButton(onClick = { onBackClicked() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clearAndSetSemantics {
                        contentDescription =
                            "Back Button. Double Tap to navigate to Dashboard Screen."
                        testTag = backButtonTag
                    })
            {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Button. Double Tap to navigate to Dashboard Screen.")
            }

            Text(text = stringResource(R.string.ProfileScreenHeader), modifier = Modifier
                .align(Alignment.Center)
                , style = MaterialTheme.typography.h6)

            if(showDoneButton){
                Text(text = stringResource(R.string.DoneButtonText),
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { onDoneClicked() }
                        .clearAndSetSemantics {
                            contentDescription = contentDescriptionForDoneButton
                            testTag = doneButtonTag
                        })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun view(){
    ProfileScreenTopBar(showDoneButton = false, onBackClicked = { /*TODO*/ }) {
        
    }
}