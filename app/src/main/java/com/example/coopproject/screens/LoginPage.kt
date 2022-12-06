package com.example.coopproject.screens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coopproject.R
import com.example.coopproject.navigation.Screens
import com.example.coopproject.screens.screenComponents.OrRow
import com.example.coopproject.screens.screenComponents.UserForm
import com.example.coopproject.ui.theme.*

@Composable
fun LoginPage(sharedViewModel: SharedViewModel,navController: NavController){
    val showToastMessage = remember {
        mutableStateOf(false)
    }
    if (showToastMessage.value){
        Toast.makeText(LocalContext.current,"Incorrect Email Address or password",Toast.LENGTH_SHORT).show()
        showToastMessage.value = false
    }
        LoginContents(
            onLoginClicked = {

                email,
                password
                ->
                sharedViewModel.signInWithEmailAndPassword(email, password = password, successfulLogin = {
                    navController.navigate(route = Screens.DASHBOARD_SCREEN.name)
                }, onUnsuccessfulLogin = {
                    showToastMessage.value = true
                })
            },
            onForgotPasswordClicked = {
                //TODO: Firebase Forgot password func.
                                      navController.navigate(route = Screens.FORGOT_PASSWORD.name)

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
                    getEmailAddress = {
                        email.value = it
                                      },
                    getPassword = {password.value = it},
                    modifier = Modifier.padding(top = PADDING_MEDIUM, start = PADDING_MEDIUM, end = PADDING_MEDIUM, bottom = PADDING_SMALL))

                Button(onClick = { onLoginClicked(email.value,password.value) },
                    enabled = email.value.isNotEmpty() && password.value.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = PADDING_MEDIUM,
                            end = PADDING_MEDIUM,
                            bottom = SMALL_THICKNESS
                        ),
                    colors = ButtonDefaults.buttonColors(LighterAppThemeColor, disabledBackgroundColor = FadedTextColor)
                    ) {
                    Text(
                        text = "Login",
                        color = Color.White,
                        style = MaterialTheme.typography.button,
                        fontWeight = FontWeight.Bold,
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
                        fontSize = 13.sp,
                        modifier = Modifier
                            .padding(bottom = PADDING_SMALL)
                            .clickable {
                                onForgotPasswordClicked()
                            }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = PADDING_SMALL)
                    ) {
                        Text(
                            text = "New Member? Click Here to",
                            color = Color.White,
                            fontSize = 13.sp,

                        )
                        Text(
                            text = " sign up.",
                            color = LighterAppThemeColor,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .clickable {
                                    onSignUpClicked()
                                }
                        )
                    }
                }


                OrRow()
                Column(modifier = Modifier.padding(top = PADDING_SMALL ,start = PADDING_MEDIUM, end = PADDING_MEDIUM, bottom = PADDING_SMALL)) {
                    ContinueWithGoogleButton (continueWithGoogleClicked = {})
                    Spacer(modifier = Modifier.height(20.dp))
                    ContinueWithFacebookButton (continueWithFacebookClicked = {})
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

