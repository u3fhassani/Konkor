package com.project.konkor.util

interface EntityMapper<Entity, AppModel> {

    fun mapFromEntity(entity: Entity): AppModel

    fun mapToEntity(appModel: AppModel): Entity

}