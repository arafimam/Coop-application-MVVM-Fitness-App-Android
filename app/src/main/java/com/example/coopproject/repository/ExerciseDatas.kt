package com.example.coopproject.repository

import com.example.coopproject.utils.SubExercise

enum class Difficulty {
    Easy,
    Hard,
    Medium
}

fun getChestExercise(difficultyLevel: String): List<SubExercise>{
    if (difficultyLevel == Difficulty.Easy.name){
        val sb1: SubExercise = SubExercise("Bench Press",10,2,2,30.0,false);
        val sb2: SubExercise = SubExercise("Push Up",5,3,15,20.0,false);
        val sb3: SubExercise = SubExercise("Inclined Bench Press",10,2,1,15.00,false)
        return listOf(sb1,sb2,sb3)
    }
    else if (difficultyLevel == Difficulty.Medium.name){
        val sb1: SubExercise = SubExercise("Chest Press",15,3,2,30.0,false);
        val sb2: SubExercise = SubExercise("Push Up",10,3,15,20.0,false);
        val sb3: SubExercise = SubExercise("Inclined Bench Press",15,3,1,15.00,false)
        return listOf(sb1,sb2,sb3)
    }
    else if (difficultyLevel == Difficulty.Hard.name){
        val sb1: SubExercise = SubExercise("Declined Bench Press",20,3,2,30.0,false);
        val sb2: SubExercise = SubExercise("Push Up",25,3,15,20.0,false);
        val sb3: SubExercise = SubExercise("Inclined Chest Press",20,3,1,15.00,false)
        val sb4: SubExercise = SubExercise("Dips",20,3,1,15.00,false)
        val sb5: SubExercise = SubExercise("Cable Iron Cross",20,3,1,15.00,false)
        return listOf(sb1,sb2,sb3,sb4,sb5)
    }
    return emptyList()
}


