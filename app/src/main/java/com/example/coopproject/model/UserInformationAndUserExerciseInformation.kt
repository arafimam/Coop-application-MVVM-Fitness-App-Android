package com.example.coopproject.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * One to One relationship between two SQL tables: UserExerciseInformation & UserInformation
 */
data class UserInformationAndUserExerciseInformation(
    @Embedded val userInformation: UserInformation,
    @Relation(
        parentColumn = "userName",
        entityColumn = "ownerName"
    )
    val userExerciseInformation: UserExerciseInformation
)
