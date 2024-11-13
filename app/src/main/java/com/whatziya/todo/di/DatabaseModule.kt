package com.whatziya.todo.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.whatziya.todo.data.db.AppDatabase
import com.whatziya.todo.data.db.ToDoDao
import com.whatziya.todo.preferences.PreferencesProvider
import com.whatziya.todo.utils.Constants
import com.whatziya.todo.utils.Constants.FileName.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return try {
            val masterKeyAlias =
                MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
            EncryptedSharedPreferences.create(
                context,
                Constants.FileName.SHARED_PREFS,
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (ignore: Exception) {
            context.getSharedPreferences(Constants.FileName.SHARED_PREFS, Context.MODE_PRIVATE)
        }
    }

    @Singleton
    @Provides
    fun providePreferencesProvider(sharedPreferences: SharedPreferences) =
        PreferencesProvider(sharedPreferences)

    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideToDoDao(roomDatabase: AppDatabase): ToDoDao {
        return roomDatabase.todoDao()
    }

}