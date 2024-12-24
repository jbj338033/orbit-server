package com.orbit.global.common.validator

interface Validator<T> {
    fun validate(target: T)
}