package com.example.coopproject.repository

import androidx.compose.ui.text.toLowerCase

// 7 days a week
// 5 days =  chest, leg, biceps, triceps and forearm, back and shoulder, abs
enum class Gender {
    Male,
    Female,
    Others
}
/**
 * Informations exercise are based on:
 * 1) Gender
 * 2) Age
 * 3) Body type
 * fun getExerciseInfo(gender,age,bodytype) -> Json STRING to be stored in database.
 */


/**
 * Gets exercise information based on:
 * @param gender String
 * @param age Integer
 * @param bodyType String
 */
fun getExerciseInfo(gender: String,age: Int,bodyType: String): String{
    return ""
}