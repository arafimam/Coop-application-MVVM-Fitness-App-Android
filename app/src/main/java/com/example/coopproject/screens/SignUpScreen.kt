package com.example.coopproject.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coopproject.model.UserExerciseInformation
import com.example.coopproject.model.UserInformation
import com.example.coopproject.navigation.Screens

@Composable
fun SignUpScreen(navController: NavController,sharedViewModel: SharedViewModel){

    val buttonState = remember {
        mutableStateOf(false)
    }


    Column() {
        Text(text = "This is the sign Up Screen.")

        Button(onClick = {
            sharedViewModel.signupUser(UserInformation(userName = "Syed", email = "araf@gogo.com", password = "123", gender = "Male", age = 21, bodyType = "Lean"));
            sharedViewModel.insertUserExerciseInformation(UserExerciseInformation(ownerName = "Syed", exerciseInformation = sharedViewModel.dummyData))
            navController.navigate(route = Screens.DASHBOARD_SCREEN.name)
        }) {
            Text(text = "Sign Up.")
        }
    }

}