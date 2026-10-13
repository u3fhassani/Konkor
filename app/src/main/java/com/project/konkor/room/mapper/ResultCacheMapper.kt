package com.project.konkor.room.mapper

import com.project.konkor.model.ResultModel
import com.project.konkor.room.entity.ResultCacheEntity
import com.project.konkor.util.EntityMapper
import javax.inject.Inject

class ResultCacheMapper
@Inject
constructor() : EntityMapper<ResultCacheEntity, ResultModel> {

    override fun mapFromEntity(entity: ResultCacheEntity): ResultModel {
        return ResultModel(
            id = entity.id,
            category = entity.category,
            year = entity.year,
            questionCount = entity.questionCount,
            responseRate = entity.responseRate,
            trueAnswer = entity.trueAnswer,
            falseAnswer = entity.falseAnswer,
            notAnswer = entity.notAnswer,
            dateTime = entity.dateTime,
            lesson = entity.lesson,
            isNewSystem = entity.isNewSystem,
            isAbroad = entity.isAbroad
        )
    }

    override fun mapToEntity(appModel: ResultModel): ResultCacheEntity {
        return ResultCacheEntity(
            id = appModel.id,
            category = appModel.category,
            year = appModel.year,
            questionCount = appModel.questionCount,
            responseRate = appModel.responseRate,
            trueAnswer = appModel.trueAnswer,
            falseAnswer = appModel.falseAnswer,
            notAnswer = appModel.notAnswer,
            dateTime = appModel.dateTime,
            lesson = appModel.lesson,
            isNewSystem = appModel.isNewSystem,
            isAbroad = appModel.isAbroad
        )
    }

    fun mapFromEntityList(entities: List<ResultCacheEntity>): List<ResultModel> {
        return entities.map { mapFromEntity(it) }
    }


}