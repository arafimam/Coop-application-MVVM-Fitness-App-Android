package com.example.coopproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_info")
data class UserExerciseInformation(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val ownerName : String,
    val exerciseInformation : String, // Stores the JSON String for the exercise information.
    val finishedWorkout : Int = 0, //initialized as 0.
    val unfinishedWorkout: Int = 0 // initialized as 0.
)
