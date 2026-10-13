package com.project.konkor.network.mapper

import com.project.konkor.model.YearModel
import com.project.konkor.network.entity.YearNetworkEntity
import com.project.konkor.util.EntityMapper
import javax.inject.Inject

class YearNetworkMapper
@Inject
constructor() : EntityMapper<YearNetworkEntity, YearModel> {

    override fun mapFromEntity(entity: YearNetworkEntity): YearModel {
        return YearModel(
            id = entity.id,
            year = entity.year,
            isNewSystem = entity.isNewSystem,
            isAbroad = entity.isAbroad
        )
    }

    override fun mapToEntity(appModel: YearModel): YearNetworkEntity {
        return YearNetworkEntity(
            id = appModel.id,
            year = appModel.year,
            isNewSystem = appModel.isNewSystem,
            isAbroad = appModel.isAbroad
        )
    }

    fun mapFromEntityList(entities: List<YearNetworkEntity>): List<YearModel> {
        return entities.map { mapFromEntity(it) }
    }


}