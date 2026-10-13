package com.project.konkor.room.mapper

import com.project.konkor.model.TargetModel
import com.project.konkor.room.entity.TargetCacheEntity
import com.project.konkor.util.EntityMapper
import javax.inject.Inject

class TargetCacheMapper
@Inject
constructor() : EntityMapper<TargetCacheEntity, TargetModel> {

    override fun mapFromEntity(entity: TargetCacheEntity): TargetModel {
        return TargetModel(
            id = entity.id,
            title = entity.title,
            date = entity.date,
            checkBox = entity.checkBox,
        )
    }

    override fun mapToEntity(appModel: TargetModel): TargetCacheEntity {
        return TargetCacheEntity(
            id = appModel.id,
            title = appModel.title,
            date = appModel.date,
            checkBox = appModel.checkBox,
        )
    }

    fun mapFromEntityList(entities: List<TargetCacheEntity>): List<TargetModel> {
        return entities.map { mapFromEntity(it) }
    }


}