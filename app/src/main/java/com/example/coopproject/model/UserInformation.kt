package com.example.coopproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_information_table")
data class UserInformation(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val userName: String,
    val email: String,
    val password: String,
    val gender: String,
    val age: Int,
    val bodyType: String,
    val notifyHourTime: Int = 8// the notify hour time of the user. Default time is 8 -> 8:00 AM as per Calendar Instance.
)
