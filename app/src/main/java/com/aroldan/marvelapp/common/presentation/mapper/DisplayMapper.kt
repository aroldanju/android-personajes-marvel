package com.aroldan.marvelapp.common.presentation.mapper

abstract class DisplayMapper <Domain, Display> {

    abstract fun map(entity: Domain): Display

    fun map(entities: List<Domain>): List<Display> =
        entities.map { map(it) }

}
