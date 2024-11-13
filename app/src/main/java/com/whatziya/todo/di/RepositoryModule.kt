package com.whatziya.todo.di

import com.whatziya.todo.data.repository.ToDoRepository
import com.whatziya.todo.data.repository.ToDoRepositoryImpl
import com.whatziya.todo.data.source.local.ToDoLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideToDoRepository(
        localDataSource: ToDoLocalDataSource
    ): ToDoRepository {
        return ToDoRepositoryImpl(localDataSource)
    }
}