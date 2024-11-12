package com.whatziya.todo.di

import com.whatziya.todo.data.db.ToDoDao
import com.whatziya.todo.data.source.local.ToDoLocalDataSource
import com.whatziya.todo.data.source.local.ToDoLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SourceModule {
    @Singleton
    @Provides
    fun provideHomeLocalDataSource(
        dao: ToDoDao
    ): ToDoLocalDataSource {
        return ToDoLocalDataSourceImpl(dao)
    }
}