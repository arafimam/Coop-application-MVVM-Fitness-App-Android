package com.example.coopproject.screens

import android.app.Activity
import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coopproject.MainActivity
import com.example.coopproject.R

import com.example.coopproject.model.UserExerciseInformation
import com.example.coopproject.model.UserInformation

import com.example.coopproject.repository.Repository
import com.example.coopproject.utils.ExerciseInformation
import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import com.facebook.login.LoginManager

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    /**
     * Dummy data for testing purposes.
     */
    var signedUpUser: String = "Syed"
    var dummyData: String = repository.createExerciseDummyInformation();
    val currentDayInfo: ExerciseInformation? = getCurrentDaysExerciseInformation(dummyData)
    var countryName = "Canada" // default is Canada

    /**
     * Data of user exercise from database.
     */
    private val _userExerciseInfoData: MutableStateFlow<UserExerciseInformation?> = MutableStateFlow(null)
    val userExerciseInfoData: StateFlow<UserExerciseInformation?> = _userExerciseInfoData

    /**
     * Data of user information from database.
     */
    private val _userInformation: MutableStateFlow<UserInformation?> = MutableStateFlow(null)
    val userInformation: StateFlow<UserInformation?> = _userInformation

    /**
     * Date for storing notification time.
     */
    private val _notifyTime: MutableStateFlow<Int?> = MutableStateFlow(null)
    val notifyTime : StateFlow<Int?> = _notifyTime

    /**
     * Data for using Firebase authentication.
     */
    val auth: FirebaseAuth = Firebase.auth


    /**
     * todo: Use the loading parameter to create a loading screen.
     */
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    /**
     * Resets Password using email.
     * @param email of the user
     * @param onEmailSent, function called when successfully Linked Sent.
     */
    fun resetPasswordUsingEmail(email: String, onEmailSent: () -> Unit){
        viewModelScope.launch {
            try{
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener{
                            task ->
                        if (task.isSuccessful){
                            onEmailSent()
                            Log.d("EMAIL","EMAIL SENT")
                        }else{
                            Log.d("EMAIL","EMAIL NOT SENT")
                        }
                    }

            }catch (exp: Exception){
                Log.d("EXCEPTION","EXCEPTION")
            }
        }
    }

    /**
     * Signs in the user with email and password.
     * @param email String
     * @param password String
     * @param successfulLogin, function called when sign in is successful
     * @param onUnsuccessfulLogin, function called when sign in is not successful
     */
    fun signInWithEmailAndPassword(email: String,password: String,successfulLogin: ()-> Unit,
    onUnsuccessfulLogin: () -> Unit){
        viewModelScope.launch {
            try{
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{
                        task ->
                            if (task.isSuccessful){
                                successfulLogin()
                                //todo: navigate to dashboard screen.
                            }
                        else{
                            onUnsuccessfulLogin()
                            Log.d("LOGIN_ERROR","Login is not successful.")
                            }
                    }
            }catch(exp: Exception){
                Log.d("Exception","FIREBASE EXCEPTION DURING LOGIN: "+ exp.message)
            }
        }
    }

    /**
     * Create user with sign up credential.
     * @param email String
     * @param password String
     * @param onSuccessfulSignUp, function called when login is successful
     */
    fun createUserWithEmailAndPassword(email:String,password: String,onSuccessfulSignUp: () -> Unit){
        viewModelScope.launch {
            try{
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{
                            task ->
                        if (task.isSuccessful){
                            onSuccessfulSignUp()
                        }else{

                            Log.d("SIGNUP_ERROR","USER IS NOT SUCCESSFULLY LOGGED IN. ${task.result}")
                        }
                    }
            }catch(exception: Exception){
                Log.d("Exception","Exception caused during signup ${exception.message}")
            }
        }
    }


    /**
     * Signs out user
     * @Frame_Condtion: auth.currentUser becomes null
     */
    fun SignOutUser(context: Context){
        auth.signOut()
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("116444876397-do0tbrpj5j042lafrriutn22ntdhf0lr.apps.googleusercontent.com")
                .requestEmail()
                .build()

        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        googleSignInClient.signOut()
        LoginManager.getInstance().logOut();
        }



    /**
     * Gets notification Time from Room Database.
     * @param userName of the user for whom we want to know the notification time.
     */
    fun getNotifyHourTime (userName: String){
        viewModelScope.launch {
            repository.getNotifyHourTime(userName).collect {
                _notifyTime.value = it
            }
        }
    }


    /**
     * Updates user notification time.
     * @param newTime new Notification Time
     * @param userName of the user for whom a new notification time is required.
     */
    fun updateNotifyHourTime(newTime: Int, userName: String){
        viewModelScope.launch (Dispatchers.IO) {
            repository.updateNotifyHourTime(newTime,userName)
        }
    }

    /**
     * Get user information of
     * @param name String of the user we want to get information.
     */
    fun getUserInformation(name: String){
        viewModelScope.launch {
            repository.getUserInformation(name).collect {
                _userInformation.value = it
            }
        }
    }

    /**
     * Gets all user information.
     */
    fun getAllUserInformationAndExerciseInformation(){
        viewModelScope.launch {
            repository.getUserInformationAndUserExerciseInformation()
        }
    }

    /**
     * Gets user exercise Information.
     */
    fun getUserExerciseInformation(name: String){
        viewModelScope.launch {
            repository.getUserExerciseInformation(name = name).collect {
                _userExerciseInfoData.value = it
            }
        }
    }

    /**
     * Sign Up User.
     * @param userInformation
     */
    fun signupUser(userInformation: UserInformation){
        viewModelScope.launch {
            repository.signupUser(userInformation)
        }
    }

    /**
     * Insert user exercise information.
     * @param userExerciseInformation
     */
    fun insertUserExerciseInformation(userExerciseInformation: UserExerciseInformation){
        viewModelScope.launch {
            repository.insertUserExerciseInformation(userExerciseInformation)
        }
    }

    /**
     * Updates user information.
     * @param name String
     * @param age Int
     * @param bodyType String
     * @param id Int
     */
    fun updateUserInfo(name: String,age:Int,bodyType: String,id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUserInformation(name = name, age = age, bodyType = bodyType,
            id = id)
        }
    }

    /**
     * Updates user exercise Info.
     * @param name String
     * @param exerciseInfo String
     * @param id Int
     */
    fun updateUserExerciseInformation(name: String,exerciseInfo: String,id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUserExerciseInfo(name = name, exerciseInformation = exerciseInfo,
                id = id)
        }
    }

    /**
     * Updates finished workout value.
     * @param finishedWorkoutValue
     * @param id
     */
    fun updateFinishedWorkoutValue(finishedWorkoutValue: Int, id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFinishedWorkoutValue(finishedVal = finishedWorkoutValue,id = id)
        }
    }

    /**
     * Updates unfinished workout
     * @param unfinishedWorkoutValue
     * @param id
     */
    fun updateUnfinishedWorkoutValue(unfinishedWorkoutValue: Int, id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUnfinishedWorkoutValue(unfinished = unfinishedWorkoutValue, id = id)
        }
    }

    fun getCurrentDaysExerciseInformation(exerciseData: String?) : ExerciseInformation?{
        var gson: Gson = Gson()
        val data: List<ExerciseInformation>? = gson.fromJson(exerciseData,object: TypeToken<List<ExerciseInformation>>() {}.type)
        if (data != null) {
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_WEEK)
            if (day-1 > data.size){
                return null;
            }
            return data[day-1]
        }
        return null
    }
}