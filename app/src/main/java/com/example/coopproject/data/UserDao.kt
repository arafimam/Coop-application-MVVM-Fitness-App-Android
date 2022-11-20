package com.example.coopproject.data

import androidx.room.*
import com.example.coopproject.model.UserExerciseInformation
import com.example.coopproject.model.UserInformation
import com.example.coopproject.model.UserInformationAndUserExerciseInformation
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    /**
     * Signs up with a user.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun signUpWithUser(userInformation: UserInformation);

    /**
     * Inserts User exercise Information.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserExerciseInformation(userExerciseInformation: UserExerciseInformation)

    @Query("SELECT * FROM exercise_info WHERE ownerName = :name")
    fun getUserExerciseInfo(name: String) : Flow<UserExerciseInformation>

    @Query("SELECT * FROM user_information_table where userName = :name")
    fun getUserInfo(name: String) : Flow<UserInformation>

    /**
     * gets all rows of the one to one table.
     * @Transaction is used to make sure the query is thread safe.
     */
    @Transaction
    @Query("SELECT * FROM user_information_table")
    fun getUserInformationAndUserExerciseInformation() :
            Flow<List<UserInformationAndUserExerciseInformation>>;

    /**
     * Updates user information using the user id to query the user table.
     */
    @Query("UPDATE user_information_table SET userName =:name, age = :ageVal, bodyType = :body WHERE id = :idNum")
    fun updateUserInformation(name: String,ageVal:Int,body: String,idNum: Int)

    /**
     * Updates user exercise info using the user id.
     */
    @Query("UPDATE exercise_info SET ownerName = :name, exerciseInformation = :exerciseInfo WHERE id = :id")
    fun updateUserExerciseInformation(name: String,exerciseInfo: String, id: Int)

    @Query("UPDATE exercise_info SET finishedWorkout = :finishedValue WHERE id = :id")
    fun updateFinishedWorkoutValue(finishedValue: Int,id:Int)

    @Query("UPDATE exercise_info SET unfinishedWorkout = :unfinishedValue WHERE id = :id")
    fun updateUnfinishedWorkoutValue(unfinishedValue: Int, id: Int)
}