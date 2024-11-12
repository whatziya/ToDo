package com.whatziya.todo.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesProvider @Inject constructor(
    private val preferences: SharedPreferences
) {
    var hideCompleted: Boolean by preferences.boolean(true)
    fun clear() {
        preferences.edit().clear().apply()
    }
}