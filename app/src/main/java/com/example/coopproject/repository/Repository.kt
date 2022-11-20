package com.example.coopproject.repository

import com.example.coopproject.data.UserDao
import com.example.coopproject.model.UserExerciseInformation
import com.example.coopproject.model.UserInformation
import com.example.coopproject.model.UserInformationAndUserExerciseInformation
import com.example.coopproject.utils.ExerciseInformation
import com.example.coopproject.utils.SubExercise
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val userDao: UserDao) {

    /**
     * Signs up with a user.
     */
    suspend fun signupUser(userInformation: UserInformation) =
        userDao.signUpWithUser(userInformation)

    /**
     * Inserts user exercise information
     */
    suspend fun insertUserExerciseInformation(userExerciseInformation: UserExerciseInformation) =
        userDao.insertUserExerciseInformation(userExerciseInformation)

    /**
     * Gets all user information of two tables.
     */
    fun getUserInformationAndUserExerciseInformation() :
            Flow<List<UserInformationAndUserExerciseInformation>> =
        userDao.getUserInformationAndUserExerciseInformation()

    /**
     * Gets user exercise information.
     * @param name String
     * @return exerciseInfo
     */
   fun getUserExerciseInformation(name: String): Flow<UserExerciseInformation> =
        userDao.getUserExerciseInfo(name)

    /**
     * Get user information
     * @Param name String
     * @return userInformation
     */
    fun getUserInformation(name: String): Flow<UserInformation> =
        userDao.getUserInfo(name)

    /**
     * Updates user information.
     */
    fun updateUserInformation(name: String,age: Int,bodyType: String, id: Int){
        userDao.updateUserInformation(name = name, ageVal = age, body = bodyType, idNum = id)
    }

    /**
     * Updates user exercise information.
     */
    fun updateUserExerciseInfo(name: String,exerciseInformation: String,id: Int){
        userDao.updateUserExerciseInformation(name = name, exerciseInfo = exerciseInformation,id = id)
    }

    /**
     * updates finished workout value.
     */
    fun updateFinishedWorkoutValue(finishedVal: Int,id: Int){
        userDao.updateFinishedWorkoutValue(finishedValue = finishedVal, id = id)
    }

    /**
     * Updates unfinished workout value.
     */
    fun updateUnfinishedWorkoutValue(unfinished: Int,id: Int){
        userDao.updateUnfinishedWorkoutValue(unfinishedValue = unfinished, id = id)
    }

    /**
     * Provides dummy data for exercise information.
     * @return JSON String
     */
    fun createExerciseDummyInformation(): String {
        var sb1: SubExercise = SubExercise("Bench Press",15,2,2,30.0,false);
        var sb2: SubExercise = SubExercise("Push Up",30,3,15,20.0,false);
        var sb3: SubExercise = SubExercise("Inclined Chest Press",10,3,1,15.00,false)
        var sb4: SubExercise = SubExercise("Declined chest press",22,3,1,15.00,false)
        var sb5: SubExercise = SubExercise("Dips",11,3,1,15.00,false)
        var sb6: SubExercise = SubExercise("Dumbbell Chest Press",22,3,1,15.00,false)
        var sb7: SubExercise = SubExercise("Shoulder Bench Press",15,3,1,15.00,false)
        var listOfSubExercise = listOf<SubExercise>(sb1,sb2,sb3,sb4,sb5,sb6,sb7)

        var ex1: ExerciseInformation = ExerciseInformation("Sunday","Chest Day",listOfSubExercise)
        var ex2: ExerciseInformation = ExerciseInformation("Monday","Leg day",listOfSubExercise)
        var ex3: ExerciseInformation = ExerciseInformation("Tuesday", "Chest Day",listOfSubExercise)
        var ex4: ExerciseInformation = ExerciseInformation("Wednesday","Shoulder Day",listOfSubExercise)
        var ex5: ExerciseInformation = ExerciseInformation("Thursday","Heavy lifting",listOfSubExercise)
        var ex6: ExerciseInformation = ExerciseInformation("Friday","Easy",listOfSubExercise)
        var ex7: ExerciseInformation = ExerciseInformation("Saturday","Chest Day",listOfSubExercise)

        var listOfExercise: List<ExerciseInformation> = listOf(ex1,ex2,ex3,ex4,ex5,ex6,ex7)
        var gson: Gson = Gson()
        return gson.toJson(listOfExercise)
    }
}