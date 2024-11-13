package com.whatziya.todo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.whatziya.todo.data.dto.ToDoEntity

@Database(entities = [ToDoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): ToDoDao
}