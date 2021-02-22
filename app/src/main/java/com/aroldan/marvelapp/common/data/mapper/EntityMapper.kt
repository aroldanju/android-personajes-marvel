package com.aroldan.marvelapp.common.data.mapper

abstract class EntityMapper <Entity, Domain> {

    abstract fun map(entity: Entity): Domain

    fun map(entities: List<Entity>): List<Domain> =
            entities.map { map(it) }

}