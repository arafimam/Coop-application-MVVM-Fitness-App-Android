package com.example.coopproject.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coopproject.model.UserExerciseInformation
import com.example.coopproject.model.UserInformation

@Database(entities = [UserInformation :: class,UserExerciseInformation :: class],
    version = 20, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao;
}