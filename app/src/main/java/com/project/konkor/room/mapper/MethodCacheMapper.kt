package com.project.konkor.room.mapper

import com.project.konkor.model.MethodModel
import com.project.konkor.room.entity.MethodCacheEntity
import com.project.konkor.util.EntityMapper
import javax.inject.Inject

class MethodCacheMapper
@Inject
constructor() : EntityMapper<MethodCacheEntity, MethodModel> {

    override fun mapFromEntity(entity: MethodCacheEntity): MethodModel {
        return MethodModel(
            id = entity.id,
            methodTitle = entity.methodTitle,
            methodDescription = entity.methodDescription,
            methodCat = entity.methodCat,
            lottieAnimation = entity.lottieAnimation
        )
    }

    override fun mapToEntity(appModel: MethodModel): MethodCacheEntity {
        return MethodCacheEntity(
            id = appModel.id,
            methodTitle = appModel.methodTitle,
            methodDescription = appModel.methodDescription,
            methodCat = appModel.methodCat,
            lottieAnimation = appModel.lottieAnimation
        )
    }


    fun mapFromEntityList(entities : List<MethodCacheEntity>): List<MethodModel> {
        return entities.map { mapFromEntity(it) }
    }

}