package com.example.coopproject.screens

import android.text.TextUtils
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coopproject.R
import com.example.coopproject.model.UserExerciseInformation
import com.example.coopproject.model.UserInformation
import com.example.coopproject.navigation.Screens
import com.example.coopproject.screens.screenComponents.OrRow
import com.example.coopproject.screens.screenComponents.UserForm
import com.example.coopproject.ui.theme.*
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Facebook
import compose.icons.fontawesomeicons.brands.Google

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

    Column {
        UserForm(
            getEmailAddress = {emailAddress.value = it},
            getPassword = {password.value = it},
            modifier = Modifier.padding(top = PADDING_MEDIUM, start = PADDING_MEDIUM, end = PADDING_MEDIUM, bottom = PADDING_SMALL))

        passwordChecker(password = password.value)
        Button(onClick = { signUpClicked(emailAddress.value,password.value) },
            enabled = password.value.length >= 6 && checkEmailAddress(emailAddress.value),
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
                text = "SignUp",
                color = Color.White,
                style = MaterialTheme.typography.button,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = PADDING_SMALL),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already a member?",
                    color = Color.White,
                    fontSize = 13.sp

                    )
                Text(
                    text = " Click Here to Login",
                    color = LighterAppThemeColor,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .clickable {
                            goToLoginClicked()
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

@Composable
fun ContinueWithFacebookButton(
    continueWithFacebookClicked: () -> Unit
){
    Button(onClick = { continueWithFacebookClicked() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = SMALL_THICKNESS
            ),
        colors = ButtonDefaults.buttonColors(LighterAppThemeColor, disabledBackgroundColor = FadedTextColor)
    ){

        Icon(imageVector = FontAwesomeIcons.Brands.Facebook, contentDescription = "Google Icon",
            tint = Color.White, modifier = Modifier
                .size(24.dp)
                .weight(3f))
        Text(text = "Continue With Facebook",
            fontSize = 13.sp,
            color = Color.White,
            modifier = Modifier.weight(7f)
        )
    }
}

@Composable
fun ContinueWithGoogleButton(
    continueWithGoogleClicked: () -> Unit
){
    Button(onClick = { continueWithGoogleClicked() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = SMALL_THICKNESS
            ),
        colors = ButtonDefaults.buttonColors(LighterAppThemeColor, disabledBackgroundColor = FadedTextColor)
    ){

        Icon(imageVector = FontAwesomeIcons.Brands.Google, contentDescription = "Google Icon",
        tint = Color.White, modifier = Modifier
                .size(24.dp)
                .weight(3f))
        Text(text = "Continue With Google",
            fontSize = 13.sp,
        color = Color.White,
            modifier = Modifier.weight(7f)
        )
    }
}

private fun checkEmailAddress(email: String): Boolean{
    if (TextUtils.isEmpty(email)) {
        return false;
    } else {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

@Composable
fun passwordChecker(password: String){
    val image = remember {
        mutableStateOf(R.drawable.password_check_1)
    }

    val color = remember {
        mutableStateOf(AppBackGroundColor)
    }
    if (password.isEmpty()){
        color.value = AppBackGroundColor
    }
    else if (password.length in 1..5){
        image.value = R.drawable.ic_baseline_clear_24
        color.value = Color.Red
    }else{
        image.value = R.drawable.password_check_1
        color.value = IconColorForExerciseCard
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = PADDING_SMALL),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Icon(painter = painterResource(id = image.value), contentDescription = "password",
        tint = color.value)
        Text(text = "Password should at least be 6 characters.",
            color = color.value,
        fontSize = 15.sp)
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
    //passwordChecker()
}