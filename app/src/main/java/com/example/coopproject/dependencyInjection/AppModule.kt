package com.example.coopproject.dependencyInjection

import android.content.Context
import androidx.room.Room
import com.example.coopproject.data.UserDao
import com.example.coopproject.data.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent :: class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabase) : UserDao =
        userDatabase.userDao()

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context : Context): UserDatabase =
        Room.databaseBuilder(context,UserDatabase::class.java,"db").
        fallbackToDestructiveMigration().
        build()
}