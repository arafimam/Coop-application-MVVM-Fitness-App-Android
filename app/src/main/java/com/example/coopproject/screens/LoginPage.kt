package com.example.coopproject.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coopproject.R
import com.example.coopproject.navigation.Screens
import com.example.coopproject.screens.screenComponents.UserForm
import com.example.coopproject.ui.theme.*

@Composable
fun LoginPage(sharedViewModel: SharedViewModel,navController: NavController){
        LoginContents(
            onLoginClicked = {
                email,
                password
                ->

            },
            onForgotPasswordClicked = {
                //TODO: Firebase Forgot password func.
            },
            onSignUpClicked = {
                navController.navigate(route = Screens.SIGNUP_SCREEN.name)
            }
        )
}

@Composable
fun LoginContents(
    onLoginClicked: (String,String) -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onSignUpClicked: () -> Unit
){
    val email = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = AppBackGroundColor,

        topBar = {
                 LoginScreenTopBar()
        },
        content = {
            Column() {
                UserForm(
                    getEmailAddress = {email.value = it},
                    getPassword = {password.value = it},
                    modifier = Modifier.padding(top = TOP_PADDING_LARGE, start = PADDING_MEDIUM, end = PADDING_MEDIUM, bottom = TOP_PADDING_LARGE))

                Button(onClick = { onLoginClicked(email.value,password.value) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = PADDING_MEDIUM,
                            end = PADDING_MEDIUM,
                            bottom = PADDING_MEDIUM
                        ),
                    colors = ButtonDefaults.buttonColors(LighterAppThemeColor)
                    ) {
                    Text(
                        text = "Login",
                        color = Color.White,
                        style = MaterialTheme.typography.button,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = PADDING_SMALL)
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Forgot Password?",
                        color = LighterAppThemeColor,
                        style = MaterialTheme.typography.button,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = PADDING_SMALL)
                            .clickable {
                                onForgotPasswordClicked()
                            }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "New Member?",
                            color = Color.White,
                            style = MaterialTheme.typography.button,
                            fontWeight = FontWeight.Bold,

                        )
                        Text(
                            text = " Click Here to sign up.",
                            color = LighterAppThemeColor,
                            style = MaterialTheme.typography.button,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable {
                                    onSignUpClicked()
                                }
                        )
                    }
                }




            }

        }
    )
}

@Composable
fun LoginScreenTopBar(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        backgroundColor = AppThemeColor,
        shape = RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp)
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
                ){
            Icon(painter = painterResource(id = R.drawable.ic_baseline_directions_walk_24), contentDescription = "WALK ICON",
            tint = Color.White, modifier = Modifier.size(80.dp))
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = "fit4u",
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

        }
    }
}

