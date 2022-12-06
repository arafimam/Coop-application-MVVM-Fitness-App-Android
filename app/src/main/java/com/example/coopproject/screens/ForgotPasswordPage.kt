package com.example.coopproject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coopproject.R
import com.example.coopproject.navigation.Screens
import com.example.coopproject.screens.screenComponents.TextField
import com.example.coopproject.ui.theme.*

@Composable
fun ForgotPasswordScreen(sharedViewModel: SharedViewModel,navController: NavController){

    val emailAddress = remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = AppBackGroundColor,
        topBar = {
            TopAppBar(
                backgroundColor = AppThemeColor,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = {  navController.navigate(route = Screens.LOGIN_SCREEN.name)},
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .clearAndSetSemantics {
                                contentDescription =
                                    "Back Button. Double Tap to navigate to Dashboard Screen."
                            })
                    {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "Back Button. Double Tap to navigate to Dashboard Screen."
                        )
                    }

                    Text(
                        text = "Forgot Password", modifier = Modifier
                            .align(Alignment.Center), style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                }

            }
        },
        content = {
            Column(
                modifier = Modifier.padding(top = TOP_PADDING_LARGE, start = PADDING_MEDIUM, end = PADDING_MEDIUM, bottom = PADDING_MEDIUM)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(bottom = BIG_PADDING),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Forgot your password?",
                        color = Color.White,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = PADDING_NORMAL)
                    )
                    Text(
                        text = "Enter your email address and we will send",
                        color = Color.White,
                        fontSize = 15.sp,
                    )
                    Text(
                        text = " you instructions to reset your password",
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }
                Text(
                    text = "Email Address",
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = PADDING_SMALL)
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(5.dp)),
                    text = emailAddress.value,
                    onTextChange = {emailAddress.value = it},
                    icon = Icons.Default.Email,
                    placeholder = "ABC@gmail.com")

                Button(onClick = {
                                 sharedViewModel.resetPasswordUsingEmail(email = emailAddress.value, onEmailSent = {

                                 })
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = PADDING_MEDIUM,
                            end = PADDING_MEDIUM,
                            bottom = PADDING_MEDIUM,
                            top = PADDING_MEDIUM
                        ),
                    colors = ButtonDefaults.buttonColors(LighterAppThemeColor)
                ) {
                    Text(
                        text = "Send Email",
                        color = Color.White,
                        style = MaterialTheme.typography.button,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
}