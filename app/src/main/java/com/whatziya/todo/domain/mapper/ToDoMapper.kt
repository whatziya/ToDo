package com.whatziya.todo.domain.mapper

import com.whatziya.todo.data.dto.ToDoEntity
import com.whatziya.todo.domain.ui_model.ToDoUIModel
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToDoLocalMapper @Inject constructor() : BaseMapper<ToDoEntity, ToDoUIModel> {
    override fun toUIModel(dto: ToDoEntity) =dto.run{
        ToDoUIModel(
            id = id,
            text = text,
            importance = importance,
            deadline = Date(),
            isCompleted = isCompleted,
            createdAt = Date(createdAt),
            modifiedAt = Date(modifiedAt)
        )
    }

    override fun toDTO(uiModel: ToDoUIModel) = uiModel.run{
        ToDoEntity(
            id = id,
            text = text,
            importance = importance,
            deadline = deadline.time,
            isCompleted = isCompleted,
            createdAt = createdAt.time,
            modifiedAt = modifiedAt.time
        )
    }
}