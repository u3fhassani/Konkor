package com.project.konkor.room.mapper

import com.project.konkor.model.LessonModel
import com.project.konkor.room.entity.FavoriteCacheEntity
import com.project.konkor.util.EntityMapper
import javax.inject.Inject

class FavoriteCacheMapper
@Inject
constructor() : EntityMapper<FavoriteCacheEntity, LessonModel> {

    override fun mapFromEntity(entity: FavoriteCacheEntity): LessonModel {
        return LessonModel(
            id = entity.id,
            lessonPersianName = entity.lessonPersianName,
            lesson = entity.lesson,
            questionCount = entity.questionCount,
            time = entity.time,
            idYear = entity.idYear,
            isNewSystem = entity.isNewSystem,
            category = entity.category,
            isAbroad = entity.isAbroad
        )
    }

    override fun mapToEntity(appModel: LessonModel): FavoriteCacheEntity {
        return FavoriteCacheEntity(
            id = appModel.id,
            lessonPersianName = appModel.lessonPersianName,
            lesson = appModel.lesson,
            questionCount = appModel.questionCount,
            time = appModel.time,
            idYear = appModel.idYear!!,
            isNewSystem = appModel.isNewSystem!!,
            isAbroad = appModel.isAbroad!!,
            category = appModel.category!!
        )
    }


    fun mapFromEntityList(entities: List<FavoriteCacheEntity>): List<LessonModel> {
        return entities.map { mapFromEntity(it) }
    }


}