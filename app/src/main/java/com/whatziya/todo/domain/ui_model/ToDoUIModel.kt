package com.whatziya.todo.domain.ui_model

import java.util.Date

data class ToDoUIModel(
    val id : String,
    var text: String,
    var importance : Int,
    val deadline : Date? = null,
    val isCompleted : Boolean,
    val createdAt : Date,
    val modifiedAt : Date
)
