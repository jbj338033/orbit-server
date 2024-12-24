package com.orbit.global.common.mapper

interface Mapper<T, D> {
    fun toDto(entity: T): D
    fun toEntity(dto: D): T
}