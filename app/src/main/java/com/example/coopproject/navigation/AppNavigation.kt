package com.example.coopproject.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.compose.rememberNavController
import com.example.coopproject.screens.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(sharedViewModel: SharedViewModel){

    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = Screens.DASHBOARD_SCREEN.name){

        /**
         * Navigation for Signup Screen.
         */
        composable(
            route = Screens.SIGNUP_SCREEN.name
        ){
            SignUpScreen(navController = navController, sharedViewModel = sharedViewModel)
        }

        /**
         * Navigation for Dashboard Screen.
         */
        composable(route = Screens.DASHBOARD_SCREEN.name,enterTransition = {_,_,-> slideInHorizontally(animationSpec = tween(500))},
            exitTransition = {_,_,-> slideOutHorizontally(animationSpec = tween(500))}){
            DashboardScreen(navController = navController, sharedViewModel = sharedViewModel)
        }

        /**
         * Navigation for insights Screen.
         */
        composable(route = Screens.INSIGHTS_SCREEN.name,
            enterTransition = {_,_,-> slideInHorizontally(animationSpec = tween(500))},
            exitTransition = {_,_,-> slideOutHorizontally(animationSpec = tween(500))}
            ){
            InsightsScreen(navController = navController, sharedViewModel = sharedViewModel)
        }

        /**
         * Navigation for profile Screen.
         */
        composable(route = Screens.PROFILE_SCREEN.name,
            enterTransition = {_,_,-> slideInHorizontally(animationSpec = tween(500))},
            exitTransition = {_,_,-> slideOutHorizontally(animationSpec = tween(500))}){
            ProfileScreen(navController = navController, sharedViewModel = sharedViewModel)
        }

        /**
         * Navigation for Exercises Screen.
         */
        composable(route = Screens.EXERCISES_SCREEN.name,
            enterTransition = {_,_,-> slideInHorizontally(animationSpec = tween(500))},
            exitTransition = {_,_,-> slideOutHorizontally(animationSpec = tween(500))}){
            ExercisesScreen(navController = navController, sharedViewModel = sharedViewModel)
        }

        /**
         * Navigate to BMI Screen.
         */
        composable(route = Screens.BMI_SCREEN.name,
            enterTransition = {_,_,-> slideInHorizontally(animationSpec = tween(500))},
            exitTransition = {_,_,-> slideOutHorizontally(animationSpec = tween(500))}){
            BMIScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
    }
}