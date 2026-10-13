package com.project.konkor.network.mapper

import com.project.konkor.model.MethodModel
import com.project.konkor.network.entity.MethodNetworkEntity
import com.project.konkor.util.EntityMapper
import javax.inject.Inject

class MethodNetworkMapper
@Inject
constructor() : EntityMapper<MethodNetworkEntity, MethodModel> {
    override fun mapFromEntity(entity: MethodNetworkEntity): MethodModel {
        return MethodModel(
            id = entity.id,
            methodTitle = entity.methodTitle,
            methodDescription = entity.methodDescription,
            methodCat = entity.methodCat,
            lottieAnimation = entity.lottieAnimation
        )
    }

    override fun mapToEntity(appModel: MethodModel): MethodNetworkEntity {
        return MethodNetworkEntity(
            id = appModel.id,
            methodTitle = appModel.methodTitle,
            methodDescription = appModel.methodDescription,
            methodCat = appModel.methodCat,
            lottieAnimation = appModel.lottieAnimation
        )
    }

    fun mapFromEntityList(entities: List<MethodNetworkEntity>): List<MethodModel> {
        return entities.map { mapFromEntity(it) }
    }

}