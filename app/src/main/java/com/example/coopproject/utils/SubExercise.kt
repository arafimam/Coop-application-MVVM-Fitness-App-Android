package com.example.coopproject.utils

class SubExercise (name: String, rep: Int, set: Int, breaks: Int,time: Double,finished:Boolean = false) {
    var nameOfSubExercise: String? = name;
    var reps: Int = rep;
    var sets: Int = set;
    var totalTimeForSubExercise: Double = time;
    var finished: Boolean = finished // by default the exercise is not finished.
}