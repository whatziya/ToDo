package com.whatziya.todo.utils

import android.app.Activity
import android.widget.Toast

fun Activity.toast(message: String, isLong: Boolean = false) {
    Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}