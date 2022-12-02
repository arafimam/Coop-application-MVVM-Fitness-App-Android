package com.example.coopproject.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coopproject.model.UserExerciseInformation
import com.example.coopproject.model.UserInformation
import com.example.coopproject.model.UserInformationAndUserExerciseInformation
import com.example.coopproject.repository.Repository
import com.example.coopproject.utils.ExerciseInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    /**
     * Dummy data for testing purposes.
     */
    var signedUpUser: String = "Syed"
    var dummyData: String = repository.createExerciseDummyInformation();
    val currentDayInfo: ExerciseInformation? = getCurrentDaysExerciseInformation(dummyData)

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

    var countryName = "Canada" // default is Canada

    private val _notifyTime: MutableStateFlow<Int?> = MutableStateFlow(null)
    val notifyTime : StateFlow<Int?> = _notifyTime


    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

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

    fun createUserWithEmailAndPassword(email:String,password: String,onSuccessfulSignUp: () -> Unit){
        viewModelScope.launch {
            try{
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{
                            task ->
                        if (task.isSuccessful){
                            onSuccessfulSignUp()
                        }else{
                            Log.d("SIGNUP_ERROR","USER IS NOT SUCCESSFULLY LOGGED IN.")
                        }
                    }
            }catch(exception: Exception){
                Log.d("Exception","Exception caused during signup ${exception.message}")
            }
        }
    }




    fun getNotifyHourTime (userName: String){
        viewModelScope.launch {
            repository.getNotifyHourTime(userName).collect {
                _notifyTime.value = it
            }
        }
    }

    fun updateNotifyHourTime(newTime: Int, userName: String){
        viewModelScope.launch (Dispatchers.IO) {
            repository.updateNotifyHourTime(newTime,userName)
        }
    }
    /**
     * Get user information of @param name.
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