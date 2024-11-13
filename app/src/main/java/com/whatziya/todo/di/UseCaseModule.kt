package com.whatziya.todo.di

import com.whatziya.todo.data.repository.ToDoRepository
import com.whatziya.todo.domain.mapper.ToDoLocalMapper
import com.whatziya.todo.domain.usecase.todo.LocalEditorUseCase
import com.whatziya.todo.domain.usecase.todo.LocalHomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideLocalHomeUseCase(
        repository: ToDoRepository,
        mapper: ToDoLocalMapper
    ): LocalHomeUseCase {
        return LocalHomeUseCase(repository, mapper)
    }

    @Provides
    fun provideLocalEditorUseCase(
        repository: ToDoRepository,
        mapper: ToDoLocalMapper
    ): LocalEditorUseCase {
        return LocalEditorUseCase(repository, mapper)
    }

}