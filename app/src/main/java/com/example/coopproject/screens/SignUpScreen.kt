package com.example.coopproject.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coopproject.model.UserExerciseInformation
import com.example.coopproject.model.UserInformation
import com.example.coopproject.navigation.Screens
import com.example.coopproject.screens.screenComponents.UserForm
import com.example.coopproject.ui.theme.*

@Composable
fun SignUpScreen(navController: NavController,sharedViewModel: SharedViewModel){

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        backgroundColor = AppBackGroundColor,
        topBar = {
                 SignUpTopBar()
        },

        content = {
            SignUpForm(signUpClicked =
            {
                    email,
                    password
                ->
                sharedViewModel.createUserWithEmailAndPassword(email,password, onSuccessfulSignUp = {
                    navController.navigate(route = Screens.DASHBOARD_SCREEN.name)
                })
            },
                goToLoginClicked = {navController.navigate(route = Screens.LOGIN_SCREEN.name)})

        }
            )

}

@Composable
fun SignUpForm(
    signUpClicked: (String,String) -> Unit, // get the email and password
    goToLoginClicked: () -> Unit
){

    val emailAddress = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    Column() {
        UserForm(
            getEmailAddress = {emailAddress.value = it},
            getPassword = {password.value = it},
            modifier = Modifier.padding(top = TOP_PADDING_LARGE, start = PADDING_MEDIUM, end = PADDING_MEDIUM, bottom = TOP_PADDING_LARGE))

        Button(onClick = { signUpClicked(emailAddress.value,password.value) },
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
                text = "SignUp",
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already a member?",
                    color = Color.White,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold,

                    )
                Text(
                    text = " Click Here to Login",
                    color = LighterAppThemeColor,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                            goToLoginClicked()
                        }
                )
            }
        }
    }

}

@Composable
fun SignUpTopBar(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        backgroundColor = AppThemeColor,
        shape = RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
           Text(text = "Welcome to fit4u", style = MaterialTheme.typography.body1,
           color = Color.White)
           Text(
               text = "Sign Up to get the best exercises",
               style = MaterialTheme.typography.body1,
               color = Color.White
           )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPageView(){
    SignUpTopBar()
}