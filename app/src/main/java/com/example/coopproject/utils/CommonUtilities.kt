package com.example.coopproject.utils

import com.example.coopproject.R
import com.example.coopproject.screens.SharedViewModel

/**
 * Gets hours in the number of seconds.
 * For eg. 3600 seconds = 1 hour.
 * @param seconds Double
 * @return Int hour(s)
 */
fun getHours(seconds: Double): Int{
    return seconds.toInt()/3600;
}

/**
 * Gets minutes in the number of seconds.
 * For eg. 60 seconds = 1 minute.
 * @param seconds Double
 * @return Int hour(s)
 */
fun getMinutes(seconds: Double): Int {
    return (seconds.toInt() % 3600) / 60
}

/**
 * Gets total time from list of subExercise
 * @param list of SubExercise
 * @return Double
 */
fun getTotalTimeFromListOfSubExercise(list: List<SubExercise>) : Double{
    var totalTime: Double = 0.0
    for (subExercise in list){
        totalTime += subExercise.totalTimeForSubExercise
    }
    return totalTime
}

/**
 * Calculates user points.
 * User points is the app score based on the user's number of finished and unfinished workout.
 * @param finishedNumber Int
 * @param unfinishedNumber Int
 * @return String
 */
fun calculateUserPoints(finishedNumber: Int?, unfinishedNumber: Int?): String{
    if (finishedNumber == 0 && unfinishedNumber==0){
        return String.format("%.1f",0f)
    }
    val totalNumberOfDays: Int = finishedNumber!! + unfinishedNumber!!;
    val percentage: Double = (finishedNumber.toDouble() / totalNumberOfDays)
    val points: Double = 5.0 * percentage
    return String.format("%.1f",points)
}

/**
 * Calculates user points percentage.
 * @param finishedNumber Int
 * @param unfinishedNumber Int
 * @return Float
 */
fun calculateUserPercentage(finishedNumber: Int?, unfinishedNumber: Int?): Float {
    val totalNumberOfDays: Int = finishedNumber!! + unfinishedNumber!!;
    return (finishedNumber.toFloat() / totalNumberOfDays)
}

/**
 * Calculates User BMI (Body Mass Index).
 * @param Weight Int
 * @param height Int
 * @return Double
 */
fun calculateBMI (Weight: Float,height: Float): Double{
    return (Weight.toDouble()/(height.toDouble()*height.toDouble()))*10000
}

/**
 * Helper function to calculate user BMI.
 * @param weightValue String (@required: Locale.English)
 * @param heightValue String (@required: Locale.English)
 * @param heightUnit String
 * @param weightUnit String
 * @return Double
 */
fun calculateBMIHelper(weightValue: String, heightValue: String, weightUnit: String,heightUnit: String): Double{
    var weightVal = weightValue.toFloat()
    var heightVal = heightValue.toFloat()
    if (weightUnit == "Pounds"){
        weightVal /= 2
    }
    if (heightUnit == "Foot"){
        heightVal *= 30.48f
    }else if (heightUnit == "meters"){
        heightVal *= 100
    }
    return calculateBMI(weightVal,heightVal)

}

/**
 * Gets Image based on exercise name.
 * @param exerciseName String
 * @return Int
 */
fun getImageBasedOnExercise(exerciseName: String) : Int{
    return if (exerciseName == "Bench Press"){
        R.drawable.benchpress
    }
    else if (exerciseName == "Push Up"){
        R.drawable.pushup
    }
    else if (exerciseName == "Inclined Chest Press"){
        R.drawable.inclinedchestpress
    }
    else if (exerciseName == "Declined chest press"){
        R.drawable.declinedbenchpress
    }
    else if (exerciseName == "Dips"){
        R.drawable.benchdips
    }
    else if (exerciseName == "Dumbbell Chest Press"){
        R.drawable.dumbellchestpress
    }
    else{
        R.drawable.shoulderbenchpress
    }
}

/**
 * Function for day translation.
 */
fun getDayMap(): Map<String,Int?>{
    return mapOf("Sunday" to R.string.day1,"Monday" to R.string.day2, "Tuesday" to R.string.day3,
        "Wednesday" to R.string.day4,"Friday" to R.string.day5, "Saturday" to R.string.day6,
        "Thursday" to R.string.day7)
}

/**
 * Function for exercise translation.
 */
fun getExerciseMap() : Map<String,Int>{
    return mapOf("Chest Day" to R.string.ex1,"Leg day" to R.string.ex2, "Shoulder Day" to R.string.ex3,
        "Heavy lifting" to R.string.ex4,"Easy" to R.string.ex5)
}

/**
 * Function for sub exercise translation.
 */
fun getSubExerciseMap(): Map<String,Int>{
    return mapOf("Bench Press" to R.string.sb1,"Push Up" to R.string.sb2,"Inclined Chest Press" to R.string.sb3,
    "Declined chest press" to R.string.sb4,"Dips" to R.string.sb5,"Dumbbell Chest Press" to R.string.sb6,"Shoulder Bench Press" to R.string.sb7
    )
}

fun getContentDescriptionOfImage(exerciseName: String): String{
    return if (exerciseName == "Bench Press"){
        "Image showing a person lying flat on a bench and gripping a bar with hands slightly wider than the shoulder width."
    }
    else if (exerciseName == "Push Up"){
        "Image showing a person in push up form."
    }
    else if (exerciseName == "Inclined Chest Press"){
        "Image showing a person lying in a inclined bench and gripping a bar with hands slightly wider than the shoulder width."
    }
    else if (exerciseName == "Declined chest press"){
        "Image showing a person lying in a inclined bench and gripping a bar with hands slightly wider than the shoulder width."
    }else if (exerciseName == "Dumbbell Chest Press"){
        "Image showing a person lying flat on a bench and gripping a dumbbell with hands slightly wider than the shoulder width."
    }
    else if (exerciseName == "Dips"){
        "Image showing a person giving dips."
    }
    else if (exerciseName == "Shoulder Bench Press"){
        "Image showing a person giving shoulder bench press."
    }else{
        "Image showing a person exercising."
    }
}

/**
 * Gets owner name from email address
 * @param email String
 * @return ownername (For ex. email - xyz@gmail.com then owner name = xyz)
 */
fun getOwnerNameFromEmail(email: String): String?{
    if (email == null){
        return  null;
    }
    val partsOfString = email.split("@")
    return partsOfString[0]
}