package com.project.konkor.network.mapper

import com.project.konkor.model.AuthModel
import com.project.konkor.network.entity.AuthNetworkEntity
import com.project.konkor.util.EntityMapper
import javax.inject.Inject

class AuthNetworkMapper
@Inject
constructor() : EntityMapper<AuthNetworkEntity,AuthModel>
{
    override fun mapFromEntity(entity: AuthNetworkEntity): AuthModel {
        return AuthModel(
            category = entity.category,
            createdAt = entity.createdAt,
            email = entity.email,
            firstName = entity.firstName,
            id = entity.id,
            lastName = entity.lastName,
            updatedAt = entity.updatedAt,
            age = entity.age
        )
    }

    override fun mapToEntity(appModel: AuthModel): AuthNetworkEntity {
        return AuthNetworkEntity(
            category = appModel.category,
            createdAt = appModel.createdAt,
            email = appModel.email,
            firstName = appModel.firstName,
            id = appModel.id,
            lastName = appModel.lastName,
            updatedAt = appModel.updatedAt,
            age = appModel.age
        )
    }

}